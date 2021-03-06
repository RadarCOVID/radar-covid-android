/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venue.router

import android.content.Context
import es.gob.radarcovid.features.venue.protocols.VenueRouter
import es.gob.radarcovid.features.venuerecord.view.VenueRecordActivity
import es.gob.radarcovid.features.venuevisited.view.VenueVisitedActivity
import javax.inject.Inject

class VenueRouterImpl @Inject constructor(
    private val context: Context
) : VenueRouter {

    override fun navigateToVenueRecord() {
        VenueRecordActivity.open(context)
    }

    override fun navigateToVenuePlaces() {
        VenueVisitedActivity.open(context)
    }
}