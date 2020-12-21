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

    fun showExposureBlockHigh(daysToHeal: Int)

    fun showExposureBlockInfected()

    fun showBackgroundEnabled(enabled: Boolean)

    fun showReportButton()

    fun hideReportButton()

    fun setFakeExposureButton()

    fun setRadarBlockChecked(checked: Boolean)

    fun setRadarBlockEnabled(enabled: Boolean)

    fun areBatteryOptimizationsIgnored(): Boolean

    fun requestIgnoreBatteryOptimizations()

    fun showUnableToReportCovidDialog()

    fun showExposureHealedDialog()

    fun showUpdateLegalTermsDialog()

    fun showShareDialog()

    fun updateContentDescriptionRadar(enabled: Boolean)

}

interface HomePresenter {

    fun viewReady(activateRadar: Boolean)

    fun onResume()

    fun onPause()

    fun onExposureBlockClick()

    fun onReportButtonClick()

    fun onButtonShareClick()

    fun doShareApp(message: String)

    fun onFakeExposureButtonClick()

    fun onFakeShowExposureHealedDialogClick()

    fun onSwitchRadarClick(currentlyEnabled: Boolean)

    fun onBatteryOptimizationsIgnored()

    fun needChangeLegalTerms(): Boolean

    fun legalTermsAccepted()

}

interface HomeRouter {

    fun navigateToExpositionDetail()

    fun navigateToCovidReport()

    fun shareApp(text: String)
}