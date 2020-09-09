/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.helpline.router

import android.app.Activity
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.features.helpline.protocols.HelplineRouter
import javax.inject.Inject

class HelplineRouterImpl @Inject constructor(
    private val activity: Activity,
    private val navigationUtils: NavigationUtils
) : HelplineRouter {

    override fun navigateToBrowser(url: String) {
        navigationUtils.navigateToBrowser(activity, url)
    }

}