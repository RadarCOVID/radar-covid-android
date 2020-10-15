/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step0.presenter

import es.gob.radarcovid.features.covidreport.form.pages.step0.protocols.Step0MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step0.protocols.Step0MyHealthView
import javax.inject.Inject

class Step0MyHealthPresenterImp @Inject constructor(
    private val view: Step0MyHealthView
) : Step0MyHealthPresenter {

    override fun viewReady() {

    }

    override fun onContinueButtonClick() {
        view.performContinueButtonClick()
    }

}