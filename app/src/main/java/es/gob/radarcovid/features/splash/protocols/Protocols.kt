/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.splash.protocols

import es.gob.radarcovid.common.view.RequestView

interface SplashView : RequestView {

    fun showNoInternetWarning()

    fun showPlayServicesRequiredDialog()

    fun showNeedUpdateDialog()

    fun reloadLabels()

    fun finish()

}

interface SplashPresenter {

    fun viewReady(activateRadar: Boolean)

    fun onResume()

    fun onNetworkRetryButtonClick()

    fun onNetworkDialogCloseButtonClick()

    fun onInstallPlayServicesButtonClick()

    fun onUpdateAppButtonClick()

}

interface SplashRouter {

    fun navigateToPlayServicesPage()

    fun navigateToOnboarding()

    fun navigateToMain(activateRadar: Boolean)

    fun navigateToPlayStore()

}