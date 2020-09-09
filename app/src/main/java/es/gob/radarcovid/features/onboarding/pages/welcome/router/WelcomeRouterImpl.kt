/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.welcome.router

import android.content.Context
import android.content.Intent
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeRouter
import es.gob.radarcovid.features.splash.view.SplashActivity
import javax.inject.Inject

class WelcomeRouterImpl @Inject constructor(private val context: Context) : WelcomeRouter {

    override fun navigateToSplash() {
        context.startActivity(Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }


}