/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.main.router

import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.R
import es.gob.radarcovid.features.helpline.view.HelplineFragment
import es.gob.radarcovid.features.home.view.HomeFragment
import es.gob.radarcovid.features.main.protocols.MainRouter
import es.gob.radarcovid.features.settings.view.SettingsFragment
import es.gob.radarcovid.features.stats.view.StatsFragment
import es.gob.radarcovid.features.venue.view.VenueFragment
import es.gob.radarcovid.features.venuerecord.view.VenueRecordActivity
import javax.inject.Inject

class MainRouterImpl @Inject constructor(private val activity: AppCompatActivity) : MainRouter {

    override fun navigateToHome(activateRadar: Boolean, manualNavigation: Boolean, backFromQr: Boolean) {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HomeFragment.newInstance(activateRadar, manualNavigation, backFromQr))
            .commit()
    }

    override fun navigateToHelpline() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HelplineFragment.newInstance())
            .commit()
    }

    override fun navigateToStats() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, StatsFragment.newInstance())
            .commit()
    }

    override fun navigateToSettings() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, SettingsFragment.newInstance())
            .commit()
    }

    override fun navigateToVenueRecord(recordInProgress: Boolean) {
        if (recordInProgress) {
            VenueRecordActivity.open(activity)
        } else {
            activity
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.wrapperContent, VenueFragment.newInstance())
                .commit()
        }
    }

    override fun navigateToVenueRecordWithQR(capturedQR: String) {
        VenueRecordActivity.openWithQR(activity, capturedQR)
    }

}