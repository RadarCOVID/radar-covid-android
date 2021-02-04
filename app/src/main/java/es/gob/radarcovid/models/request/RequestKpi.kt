/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.models.request

import java.util.*

data class RequestKpi(
    val salt: String,
    val signedAttestation: String,
    val kpis: ArrayList<Kpi>
)

data class Kpi(
    val kpi: String,
    val timestamp: String,
    val value: Int
)