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

import android.content.Context
import es.gob.radarcovid.BuildConfig
import io.reactivex.rxjava3.core.Completable
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.models.ExposeeAuthMethodAuthorization
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

class FakeInfectionReportRepositoryImpl @Inject constructor(
    @Named("applicationContext") private val context: Context
) : FakeInfectionReportRepository {

    override fun notifyFakeInfected(authCode: String): Completable =
        Completable.create {
            if (BuildConfig.isMock) {
                it.onComplete()
            } else {
                DP3T.sendFakeInfectedRequest(context,
                    ExposeeAuthMethodAuthorization("Bearer $authCode"),
                    Random.nextInt(0, 2).toString(),
                    { it.onComplete() },
                    { it.onError(Exception("Error notifying fake infection")) }
                )
            }
        }

}