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

import android.util.Log
import es.gob.radarcovid.datamanager.usecase.ReportInfectedUseCase
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportRouter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import es.gob.radarcovid.models.exception.NetworkUnavailableException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class CovidReportPresenterImpl @Inject constructor(
    private val view: CovidReportView,
    private val router: CovidReportRouter,
    private val reportInfectedUseCase: ReportInfectedUseCase
) : CovidReportPresenter {

    override fun viewReady() {

    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onCodeChanged(code: String) {
        view.setButtonSendEnabled(code.length == 12)
    }

    override fun onRetryButtonClick() {
        onSendButtonClick()
    }

    override fun onSendButtonClick() {
        reportInfected(view.getReportCode(), view.getDateSelected())
    }

    override fun onSelectDateClick() {
        view.showDatePickerDialog()
    }

    private fun reportInfected(reportCode: String, date: Date?) {
        view.showLoading()
        reportInfectedUseCase.reportInfected(reportCode, date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideLoading()
                view.finish()
                router.navigateToConfirmation()
            }, {
                Log.e("CovidReportPresenter", "Error reporting infected", it)
                if (it is NetworkUnavailableException) {
                    view.hideLoadingWithNetworkError()
                } else {
                    view.hideLoadingWithErrorOnReport()
                }
            })
    }

}