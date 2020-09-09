/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.welcome.presenter

import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomePresenter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeView
import javax.inject.Inject

class WelcomePresenterImpl @Inject constructor(
    private val view: WelcomeView
) : WelcomePresenter {

    override fun viewReady() {

    }

    override fun onContinueButtonClick() {
        if (view.isLocaleChanged()) {
            view.showLanguageChangeDialog()
        } else {
            view.performContinueButtonClick()
        }
    }

    override fun onLocaleChangeConfirm() {
        view.applyLocaleSettings()
    }

    override fun onLocaleChangeCancel() {
        view.restorePreviousLanguage()
    }

}