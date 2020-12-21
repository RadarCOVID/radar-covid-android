/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.models.response

import java.util.*

class ResponseStats : ArrayList<ResponseStat>()

data class ResponseStat(
    val date: Date,
    val applicationsDownloads: TotalDataResponse,
    val communicatedContagions: TotalDataResponse
)

data class TotalDataResponse(
    val totalActualDay: Int,
    val totalAcummulated: Int
)