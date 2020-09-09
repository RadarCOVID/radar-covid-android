/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.locale.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionRouter
import es.gob.radarcovid.features.splash.view.SplashActivity
import javax.inject.Inject

class LocaleSelectionRouterImpl @Inject constructor(
    private val context: Context
) : LocaleSelectionRouter {

    override fun restartApplication() {
        (context as? Activity)?.finish()
        context.startActivity(Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

}