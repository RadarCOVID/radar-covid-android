/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step2.router

import android.content.Context
import es.gob.radarcovid.features.covidreport.confirmation.ConfirmationActivity
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthRouter
import javax.inject.Inject

class Step2MyHealthRouterImp @Inject constructor(private val context: Context) :
    Step2MyHealthRouter {

    override fun navigateToConfirmation() {
        ConfirmationActivity.open(context)
    }

}