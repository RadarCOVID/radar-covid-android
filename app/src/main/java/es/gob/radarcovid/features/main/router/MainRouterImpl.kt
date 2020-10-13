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
import es.gob.radarcovid.features.mydata.view.MyDataFragment
import es.gob.radarcovid.features.settings.view.SettingsFragment
import javax.inject.Inject

class MainRouterImpl @Inject constructor(private val activity: AppCompatActivity) : MainRouter {

    override fun navigateToHome(activateRadar: Boolean) {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HomeFragment.newInstance(activateRadar))
            .commit()
    }

    override fun navigateToProfile() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, MyDataFragment.newInstance())
            .commit()
    }

    override fun navigateToHelpline() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HelplineFragment.newInstance())
            .commit()
    }

    override fun navigateToSettings() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, SettingsFragment.newInstance())
            .commit()
    }

}