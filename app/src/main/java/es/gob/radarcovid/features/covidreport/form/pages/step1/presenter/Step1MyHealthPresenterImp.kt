/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step1.presenter

import es.gob.radarcovid.features.covidreport.form.pages.step1.protocols.Step1MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step1.protocols.Step1MyHealthView
import javax.inject.Inject

class Step1MyHealthPresenterImp @Inject constructor(
    private val view: Step1MyHealthView
) : Step1MyHealthPresenter {

    override fun onContinueButtonClick() {
        view.performContinueButtonClick(view.getReportCode(), view.getDateSelected())
    }

    override fun onBackButtonClick() {
        view.performBackButtonClick()
    }

    override fun onSelectDateClick() {
        view.showDatePickerDialog()
    }

    override fun onCodeChanged(code: String) {
        view.setButtonContinueEnabled(code.length == 12)
    }

    override fun onCancel() {
        view.finish()
    }

}