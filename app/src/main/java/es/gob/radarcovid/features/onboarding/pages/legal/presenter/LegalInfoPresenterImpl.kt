/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.legal.presenter

import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoRouter
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoView
import javax.inject.Inject

class LegalInfoPresenterImpl @Inject constructor(
    private val view: LegalInfoView,
    private val router: LegalInfoRouter
) : LegalInfoPresenter {

    override fun viewReady() {

    }

    override fun onConditionsButtonClick() {
        router.navigateToConditions()
    }

    override fun onPrivacyPolicyButtonClick() {
        router.navigateToPrivacyPolicy()
    }

    override fun onLegalTermsCheckedChange(checked: Boolean) {

        view.setContinueButtonEnabled(checked)

    }

}