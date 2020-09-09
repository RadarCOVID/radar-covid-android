/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.protocols

interface OnboardingView {

    fun showPreviousPage()

    fun showNextPage()

    fun isBluetoothEnabled(): Boolean

    fun showBluetoothRequest()

    fun showExitConfirmationDialog()

    fun finish()

}

interface OnboardingPresenter {

    fun viewReady()

    fun onExitConfirmed()

    fun onBackButtonPressed(isFirstItem: Boolean)

    fun onContinueButtonClick()

    fun onFinishButtonClick(activateRadar: Boolean)

    fun onBluetoothEnabled()

}

interface OnboardingRouter {

    fun navigateToMain(activateRadar: Boolean)

    fun navigateToSplash()

}