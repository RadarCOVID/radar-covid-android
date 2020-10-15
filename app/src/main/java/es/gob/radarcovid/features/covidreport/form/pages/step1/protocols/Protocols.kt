/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step1.protocols

import es.gob.radarcovid.common.view.RequestView
import java.util.*

interface Step1MyHealthView : RequestView {

    fun getReportCode(): String

    fun setButtonContinueEnabled(enabled: Boolean)

    fun showDatePickerDialog()

    fun getDateSelected(): Date?

    fun performContinueButtonClick()

    fun performBackButtonClick()

}

interface Step1MyHealthPresenter {

    fun onContinueButtonClick()

    fun onBackButtonClick()

    fun onSelectDateClick()

    fun onCodeChanged(code: String)

}