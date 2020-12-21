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

import es.gob.radarcovid.models.domain.StatsData
import es.gob.radarcovid.models.domain.TotalData
import es.gob.radarcovid.models.response.ResponseStat
import es.gob.radarcovid.models.response.ResponseStats
import es.gob.radarcovid.models.response.TotalDataResponse
import javax.inject.Inject

class StatsDataMapper @Inject constructor() {

    fun transform(responseStats: ResponseStats?): List<StatsData> = responseStats?.let {
        it.map { responseStatItem -> transform(responseStatItem) }
    } ?: emptyList()

    private fun transform(responseStat: ResponseStat?): StatsData =
        responseStat?.let {
            StatsData(
                date = it.date,
                applicationsDownloads = transform(it.applicationsDownloads),
                communicatedContagions = transform(it.communicatedContagions)
            )
        } ?: StatsData()


    private fun transform(applicationsDownloads: TotalDataResponse): TotalData =
        applicationsDownloads?.let {
            TotalData(
                totalActualDay = it.totalActualDay,
                totalAcummulated = it.totalAcummulated
            )
        }
}