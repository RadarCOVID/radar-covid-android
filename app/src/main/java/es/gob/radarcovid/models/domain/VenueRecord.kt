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

enum class VenueVisitedRecyclerType {
    HEADER, VENUE
}

data class VenueRecord(
    var qr: String,
    var checkOutId: Long? = null,
    var name: String? = null,
    var isExposed: Boolean = false,
    var hidden: Boolean = false,
    var dateIn: Date,
    var dateOut: Date? = null,
    var isNotified: Boolean = false,
    var moreFiveHours: Boolean = false
)

abstract class VenueVisitedRecyclerItem(
    var viewType: VenueVisitedRecyclerType
)

data class VenueVisitedItem(
    var venueItem : VenueRecord
): VenueVisitedRecyclerItem(viewType = VenueVisitedRecyclerType.VENUE)

data class VenueHeaderItem(
    var date: Date
): VenueVisitedRecyclerItem(viewType = VenueVisitedRecyclerType.HEADER)