/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.home.router

import android.content.Context
import android.content.Intent
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.view.ExposureActivity
import es.gob.radarcovid.features.home.protocols.HomeRouter
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(
    private val context: Context
) : HomeRouter {

    override fun navigateToExpositionDetail(venueExposure: Boolean) = ExposureActivity.open(context, venueExposure)

    override fun navigateToCovidReport() = CovidReportActivity.open(context, null)

    override fun shareApp(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

}