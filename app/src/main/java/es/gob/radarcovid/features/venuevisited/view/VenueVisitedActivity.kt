/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuevisited.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedPresenter
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedView
import javax.inject.Inject

class VenueVisitedActivity : BaseBackNavigationActivity(), VenueVisitedView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, VenueVisitedActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: VenueVisitedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_visited)
    }

}