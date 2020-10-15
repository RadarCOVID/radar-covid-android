/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step2.presenter

import android.util.Log
import es.gob.radarcovid.datamanager.usecase.ReportInfectedUseCase
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthRouter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthView
import es.gob.radarcovid.models.exception.NetworkUnavailableException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class Step2MyHealthPresenterImp @Inject constructor(
    private val view: Step2MyHealthView,
    private val router: Step2MyHealthRouter,
    private val reportInfectedUseCase: ReportInfectedUseCase
) : Step2MyHealthPresenter {

    override fun viewReady() {

    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onRetryButtonClick() {
        onSendButtonClick()
    }

    override fun onSendButtonClick() {
        reportInfected(view.getReportCode()!!, view.getDateSelected(), view.getSharedDiagnostic())
    }

    override fun onBackButtonClick() {
        view.performBackButtonClick()
    }

    override fun onCancel() {
        view.onCancelClick()
    }

    private fun reportInfected(reportCode: String, date: Date?, codeShared: Int) {
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
                } else if (it is java.util.concurrent.CancellationException) {
                    view.hideLoading()
                } else {
                    view.hideLoadingWithErrorOnReport()
                }
            })
    }


}