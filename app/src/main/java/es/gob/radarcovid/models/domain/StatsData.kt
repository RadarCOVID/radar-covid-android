/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.models.domain

import java.util.*

data class StatsRegionData(
    val statsData:  List<StatsData>,
    val countries: List<Region>
)

data class StatsData(
    val date: Date = Date(),
    val applicationsDownloads: TotalData = TotalData(),
    val communicatedContagions: TotalData = TotalData()
)

data class TotalData(
    val totalActualDay: Int = 0,
    val totalAcummulated: Int = 0
)