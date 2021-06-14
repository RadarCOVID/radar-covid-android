/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.presenter

import es.gob.radarcovid.datamanager.usecase.GetLocaleInfoUseCase
import es.gob.radarcovid.features.onboarding.protocols.OnboardingPresenter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingRouter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingView
import javax.inject.Inject

class OnboardingPresenterImpl @Inject constructor(
    private val view: OnboardingView,
    private val router: OnboardingRouter,
    private val getLocaleInfoUseCase: GetLocaleInfoUseCase
) : OnboardingPresenter {

    override fun viewReady() {
        getLocaleInfoUseCase.resetLanguageChanged()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onBackButtonPressed(isFirstItem: Boolean) {
        if (isFirstItem)
            view.showExitConfirmationDialog()
        else
            view.showPreviousPage()
    }

    override fun onContinueButtonClick() {
        view.showNextPage()
    }

    override fun onFinishButtonClick(activateRadar: Boolean) {
        if (activateRadar) {
            if (view.isBluetoothEnabled()) {
                router.navigateToMain(true)
                view.finish()
            } else {
                view.showBluetoothRequest()
            }
        } else {
            router.navigateToMain(false)
            view.finish()
        }
    }

    override fun onBluetoothEnabled() {
        router.navigateToMain(true)
        view.finish()
    }

}