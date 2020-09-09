/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.home.protocols

import es.gob.radarcovid.common.view.RequestView

interface HomeView : RequestView {

    fun showInitializationCheckAnimation()

    fun showExposureBlockLow()

    fun showExposureBlockHigh()

    fun showExposureBlockInfected()

    fun showBackgroundEnabled(enabled: Boolean)

    fun showReportButton()

    fun hideReportButton()

    fun setRadarBlockChecked(checked: Boolean)

    fun setRadarBlockEnabled(enabled: Boolean)

    fun areBatteryOptimizationsIgnored(): Boolean

    fun requestIgnoreBatteryOptimizations()

    fun showUnableToReportCovidDialog()

    fun showExposureHealedDialog()

}

interface HomePresenter {

    fun viewReady(activateRadar: Boolean)

    fun onResume()

    fun onPause()

    fun onExposureBlockClick()

    fun onMoreInfoButtonClick(url: String)

    fun onReportButtonClick()

    fun onBackgroundImageLongClick()

    fun onSwitchRadarClick(currentlyEnabled: Boolean)

    fun onBatteryOptimizationsIgnored()

}

interface HomeRouter {

    fun navigateToExpositionDetail()

    fun navigateToCovidReport()

    fun navigateToBrowser(url: String)

}