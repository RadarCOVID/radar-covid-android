/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.legal.router

import androidx.fragment.app.Fragment
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoRouter
import javax.inject.Inject


class LegalInfoRouterImpl @Inject constructor(
    private val fragment: Fragment,
    private val navigationUtils: NavigationUtils,
    private val labelManager: LabelManager
) : LegalInfoRouter {

    override fun navigateToConditions() {
        navigationUtils.navigateToBrowser(
            fragment.context!!,
            labelManager.getText("USE_CONDITIONS_URL", Constants.URL_USAGE_CONDITIONS).toString()
        )
    }

    override fun navigateToPrivacyPolicy() {
        navigationUtils.navigateToBrowser(
            fragment.context!!,
            labelManager.getText("PRIVACY_POLICY_URL", Constants.URL_PRIVACY_POLICY).toString()
        )
    }

}