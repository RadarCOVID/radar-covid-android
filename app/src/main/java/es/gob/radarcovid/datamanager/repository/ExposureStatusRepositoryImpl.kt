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
import es.gob.radarcovid.datamanager.mapper.ExposureInfoDataMapper
import es.gob.radarcovid.models.domain.ExposureInfo
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.internal.AppConfigManager
import org.dpppt.android.sdk.internal.storage.ExposureDayStorage
import org.dpppt.android.sdk.models.DayDate
import org.dpppt.android.sdk.models.ExposureDay
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class ExposureStatusRepositoryImpl @Inject constructor(
    @Named("applicationContext") private val context: Context,
    private val preferencesRepository: PreferencesRepository,
    private val exposureInfoDataMapper: ExposureInfoDataMapper
) : ExposureStatusRepository {

    override fun getExposureInfo(): ExposureInfo =
        exposureInfoDataMapper.transform(
            DP3T.getStatus(context),
            preferencesRepository.getInfectionReportDate()
        )

    override fun addFakeExposureDate() {
        with(ExposureDayStorage.getInstance(context)) {
            val firstExposureDay =
                exposureDays.minBy { it.exposedDate.getStartOfDay(Calendar.getInstance().timeZone) }
            val newExposureDay = if (firstExposureDay != null)
                ExposureDay(
                    -1,
                    firstExposureDay.exposedDate.subtractDays(1),
                    System.currentTimeMillis()
                )
            else
                ExposureDay(-1, DayDate().subtractDays(1), System.currentTimeMillis())

            addExposureDays(context, listOf(newExposureDay))
        }

    }

    override fun resetExposure() {
        with(AppConfigManager.getInstance(context)) {
            if (!iAmInfectedIsResettable)
                iAmInfectedIsResettable = true
        }
        DP3T.resetExposureDays(context)
        DP3T.resetInfectionStatus(context)
        preferencesRepository.setExposureAnalyticsCount(0)
    }

    override fun resetExposureDays() {
        DP3T.resetExposureDays(context)
        preferencesRepository.setExposureAnalyticsCount(0)
    }
}