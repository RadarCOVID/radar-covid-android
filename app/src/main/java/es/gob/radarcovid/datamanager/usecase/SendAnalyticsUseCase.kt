/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.usecase

import android.os.Handler
import es.gob.radarcovid.common.base.Constants.KPI_MATCH_CONFIRMED
import es.gob.radarcovid.common.extensions.DATE_FORMAT_TIMESTAMP
import es.gob.radarcovid.common.extensions.toTimeStamp
import es.gob.radarcovid.datamanager.attestation.AttestationClient
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.AnalyticsOperationalInfo
import es.gob.radarcovid.models.domain.ExposureInfo
import es.gob.radarcovid.models.request.Kpi
import es.gob.radarcovid.models.request.RequestKpi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SendAnalyticsUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val attestationClient: AttestationClient
) {

    companion object {
        private fun encodeToBase64String(data: ByteArray): String {
            return android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP)
        }

        private fun randomSalt(): String {
            val salt = ByteArray(16)
            SecureRandom().nextBytes(salt)
            return encodeToBase64String(salt)
        }
    }

    fun getAnalyticsPeriod(): Int = preferencesRepository.getAnalyticsPeriod()

    fun sendAnalyticsData(exposureInfo: ExposureInfo) {

        //*** KPI - Exposure Analytics ***
        //Check exposure days to send
        var exposureAnaliticsCount = preferencesRepository.getExposureAnalyticsCount()
        if (exposureInfo.exposureDates.size > exposureAnaliticsCount) {
            val exposureDay = exposureInfo.exposureDates[exposureAnaliticsCount]

            //Send real exposure day
            sendInfo(exposureDay, false, 0)

            preferencesRepository.setExposureAnalyticsCount(++exposureAnaliticsCount)
        } else {
            //Send dummy
            sendInfo(Date(), true, 0)
        }

    }

    private fun sendInfo(date: Date, isDummy: Boolean, retryCount: Int) {

        val dateResult = SimpleDateFormat(
            DATE_FORMAT_TIMESTAMP,
            Locale.getDefault()
        ).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(date.toTimeStamp())

        val operationalInfo = AnalyticsOperationalInfo(
            kpi = KPI_MATCH_CONFIRMED,
            date = dateResult.time,
            value = if (isDummy) 0 else 1,
            salt = randomSalt()
        )

        val sha256digest = MessageDigest
            .getInstance("SHA-256")
            .digest(operationalInfo.digest.toByteArray())

        attestationClient.attest(
            onSuccess = { result ->
                if (result != null) {
                    val requestKpi = RequestKpi(
                        salt = operationalInfo.salt,
                        signedAttestation = result,
                        kpis = arrayListOf(
                            Kpi(
                                kpi = KPI_MATCH_CONFIRMED,
                                timestamp = date.toTimeStamp(),
                                value = if (isDummy) 0 else 1
                            )
                        )
                    )

                    sendKpiObservable(requestKpi).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }
            },
            onError = { e ->
                val newRetryCount = retryCount + 1
                if (newRetryCount <= 4) {
                    val delayMillis = retryCount * retryCount * 10 * 1000L
                    Handler().postDelayed({
                        sendInfo(date, isDummy, newRetryCount)
                    }, delayMillis)
                }
            },
            nonce = encodeToBase64String(sha256digest)
        )

    }

    private fun sendKpiObservable(requestKpi: RequestKpi): Completable =
        Completable.create {
            val result = apiRepository.sendKpi(requestKpi)
            if (result.isLeft()) {
                it.onError(result.left().get())
            } else {
                it.onComplete()
            }
        }

}