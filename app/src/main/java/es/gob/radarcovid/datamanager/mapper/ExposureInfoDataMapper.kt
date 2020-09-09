/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.domain.ExposureInfo
import org.dpppt.android.sdk.InfectionStatus
import org.dpppt.android.sdk.TracingStatus
import org.dpppt.android.sdk.models.ExposureDay
import java.util.*
import javax.inject.Inject

class ExposureInfoDataMapper @Inject constructor() {

    fun transform(
        tracingStatus: TracingStatus?,
        infectionReportDate: Date?
    ): ExposureInfo {
        val exposureDates = transform(tracingStatus?.exposureDays)
        return ExposureInfo(
            level = transform(tracingStatus?.infectionStatus),
            lastUpdateTime = Date(tracingStatus?.lastSyncDate ?: 0),
            exposureDates = exposureDates,
            lastExposureDate = infectionReportDate ?: exposureDates.max() ?: Date(),
            exposureNotificationsEnabled = tracingStatus?.errors?.let {
                !it.contains(TracingStatus.ErrorState.GAEN_UNEXPECTEDLY_DISABLED)
            } ?: true
        )
    }

    private fun transform(infectionStatus: InfectionStatus?): ExposureInfo.Level {
        return infectionStatus?.let {
            when (it) {
                InfectionStatus.HEALTHY -> ExposureInfo.Level.LOW
                InfectionStatus.EXPOSED -> ExposureInfo.Level.HIGH
                InfectionStatus.INFECTED -> ExposureInfo.Level.INFECTED
            }
        } ?: ExposureInfo.Level.LOW
    }

    private fun transform(exposureDays: List<ExposureDay>?): List<Date> =
        exposureDays?.map { Date(it.exposedDate.getStartOfDay(Calendar.getInstance().timeZone)) }
            ?: emptyList()

}