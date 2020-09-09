/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.region.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoRouter
import javax.inject.Inject

class RegionInfoRouterImpl @Inject constructor(
    private val context: Context,
    private val navigationUtils: NavigationUtils
) : RegionInfoRouter {

    override fun navigateToDialer(phone: String) {
        context.startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phone}")
        })
    }

    override fun navigateToBrowser(url: String) {
        navigationUtils.navigateToBrowser(context, url)
    }

}