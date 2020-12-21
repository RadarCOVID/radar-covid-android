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

interface CovidReportView {

    fun showPage(index: Int)

    fun showPreviousPage()

    fun showNextPage()

    fun showExitConfirmationDialog()

    fun finish()

}

interface CovidReportPresenter {

    fun viewReady(incomingRepoortCode: String?)

    fun onExitConfirmed()

    fun onBackButtonPressed(isFirstItem: Boolean)

    fun onContinueButtonClick()

    fun onBackButtonClick()

    fun onFinishButtonClick()

}