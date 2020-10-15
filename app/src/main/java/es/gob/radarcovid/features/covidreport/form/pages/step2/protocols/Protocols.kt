/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step2.protocols

import es.gob.radarcovid.common.view.RequestView
import java.util.*

interface Step2MyHealthView : RequestView {

    fun showExitConfirmationDialog()

    fun hideLoadingWithNetworkError()

    fun hideLoadingWithErrorOnReport()

    fun getReportCode(): String?

    fun getDateSelected(): Date?

    fun getSharedDiagnostic(): Int

    fun finish()

    fun performBackButtonClick()
}

interface Step2MyHealthPresenter {

    fun viewReady()

    fun onBackPressed()

    fun onExitConfirmed()

    fun onRetryButtonClick()

    fun onSendButtonClick()

    fun onBackButtonClick()

}

interface Step2MyHealthRouter {

    fun navigateToConfirmation()

}