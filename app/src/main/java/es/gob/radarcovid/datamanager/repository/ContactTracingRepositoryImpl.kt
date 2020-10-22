/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.repository

import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.models.domain.Settings
import io.reactivex.rxjava3.core.Completable
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.GaenAvailability
import org.dpppt.android.sdk.backend.ResponseCallback
import org.dpppt.android.sdk.internal.nearby.GoogleExposureClient
import org.dpppt.android.sdk.models.ExposeeAuthMethodAuthorization
import java.util.*
import javax.inject.Inject

@PerActivity
class ContactTracingRepositoryImpl @Inject constructor(
    private val activity: AppCompatActivity
) : ContactTracingRepository {

    override fun checkGaenAvailability(callback: (Boolean) -> Unit) {
        if (BuildConfig.isMock) {
            callback(true)
        } else {
            DP3T.checkGaenAvailability(activity) { availability ->
                val result = when (availability) {
                    GaenAvailability.AVAILABLE -> true
                    else -> false
                }
                callback(result)
            }
        }
    }

    override fun updateTracingSettings(settings: Settings) {
        GoogleExposureClient.getInstance(activity)
            .setParams(settings.attenuationThresholdLow, settings.attenuationThresholdMedium)
        DP3T.setMatchingParameters(
            activity,
            settings.attenuationThresholdLow,
            settings.attenuationThresholdMedium,
            settings.attenuationFactorLow,
            settings.attenuationFactorMedium,
            settings.minDurationForExposure
        )
    }

    override fun startRadar(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit
    ) {
        if (BuildConfig.isMock)
            onSuccess()
        else
            DP3T.start(activity,
                {
                    onSuccess()
                },
                { exception ->
                    onError(exception)
                },
                {
                    onCancelled.invoke()
                })
    }

    override fun stopRadar() {
        DP3T.stop(activity)
    }

    override fun isRadarEnabled(): Boolean = DP3T.isTracingEnabled(activity)

    override fun syncData() {
        DP3T.sync(activity)
    }

    override fun clearData() {
        DP3T.clearData(activity)
    }

    override fun notifyInfected(
        authCode: String,
        onSet: Date
    ): Completable {
        return Completable.create {
            if (BuildConfig.isMock) {
                it.onComplete()
            } else {
                DP3T.sendIAmInfected(activity,
                    onSet,
                    ExposeeAuthMethodAuthorization("Bearer $authCode"),
                    object : ResponseCallback<Void> {
                        override fun onSuccess(response: Void?) {
                            it.onComplete()
                        }

                        override fun onError(throwable: Throwable?) {
                            it.onError(throwable ?: Exception("Error notifying infection"))
                        }
                    })
            }
        }
    }

}