/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.presenter


import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import javax.inject.Inject

class CovidReportPresenterImpl @Inject constructor(
    private val view: CovidReportView
) : CovidReportPresenter {

    override fun viewReady(incomingRepoortCode: String?) {
        if (incomingRepoortCode != null) {
            view.showPage(1)
        }
    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onBackButtonPressed(isFirstItem: Boolean) {
        if (isFirstItem)
            view.showExitConfirmationDialog()
        else
            view.showPreviousPage()
    }

    override fun onContinueButtonClick() {
        view.showNextPage()
    }

    override fun onBackButtonClick() {
        view.showPreviousPage()
    }

    override fun onFinishButtonClick() {
        view.showExitConfirmationDialog()
    }


}