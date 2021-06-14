/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.router

import android.content.Context
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.protocols.ExposureRouter
import javax.inject.Inject

class ExposureRouterImpl @Inject constructor(
    private val context: Context
) : ExposureRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context, null)

}