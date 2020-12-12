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

import es.gob.radarcovid.common.base.utils.JwtTokenUtils
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.exception.GenericRequestException
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.ResponseToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(
    private val contactTracingRepository: ContactTracingRepository,
    private val preferencesRepository: PreferencesRepository,
    private val apiRepository: ApiRepository,
    private val jwtTokenUtils: JwtTokenUtils
) {

    fun reportInfected(reportCode: String, date: Date?, sharingCode: Int): Completable =
        if (reportCode == ReportFakeInfectionUseCase.FAKE_REPORT_CODE) {
            Completable.create { it.onError(GenericRequestException()) }
        } else {
            getVerifyToken(reportCode, date, sharingCode).flatMapCompletable {
                contactTracingRepository.notifyInfected(it.token, jwtTokenUtils.getOnset(it.token))
            }.concatWith {
                preferencesRepository.setInfectionReportDate(Date())
                it.onComplete()
            }
        }

    private fun getVerifyToken(reportCode: String, date: Date?, sharingCode: Int): Observable<ResponseToken> {
        return Observable.create { emitter ->
            val result = apiRepository.verifyCode(RequestVerifyCode(date, reportCode), sharingCode.toString())

            if (result.isLeft()) {
                emitter.onError(result.left().get())
            } else {
                emitter.onNext(result.right().get())
                emitter.onComplete()
            }

        }
    }

}