/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.main.protocols

import es.gob.radarcovid.common.view.RequestView

interface MainView : RequestView {

    fun showExitConfirmationDialog()

    fun createNotificationReminder(time: Int)

    fun cancelNotificationReminder()

    fun setSettingSelected()

    fun finish()

}

interface MainPresenter {

    fun viewReady(activateRadar: Boolean)

    fun onResume()

    fun onStop()

    fun onRestart()

    fun onHomeButtonClick()

    fun onProfileButtonClick()

    fun onHelplineButtonClick()

    fun onStatsButtonClick()

    fun onSettingsButtonClick()

    fun onBackPressed()

    fun onExitConfirmed()

}

interface MainRouter {

    fun navigateToHome(activateRadar: Boolean, manualNavigation: Boolean)

    fun navigateToProfile()

    fun navigateToHelpline()

    fun navigateToStats()

    fun navigateToSettings()
}