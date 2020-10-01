/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.protocols

import es.gob.radarcovid.common.view.RequestView
import java.util.*

interface CovidReportView : RequestView {

    fun showExitConfirmationDialog()

    fun getReportCode(): String

    fun setButtonSendEnabled(enabled: Boolean)

    fun hideLoadingWithNetworkError()

    fun hideLoadingWithErrorOnReport()
    
    fun finish()

    fun showDatePickerDialog()
    
    fun getDateSelected(): Date?

}

interface CovidReportPresenter {

    fun viewReady()

    fun onBackPressed()

    fun onExitConfirmed()

    fun onCodeChanged(code: String)

    fun onRetryButtonClick()

    fun onSendButtonClick()

    fun onSelectDateClick()

}

interface CovidReportRouter {

    fun navigateToConfirmation()

}