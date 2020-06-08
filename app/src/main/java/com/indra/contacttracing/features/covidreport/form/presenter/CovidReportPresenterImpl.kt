package com.indra.contacttracing.features.covidreport.form.presenter

import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportPresenter
import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportRouter
import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportView
import javax.inject.Inject

class CovidReportPresenterImpl @Inject constructor(
    private val view: CovidReportView,
    private val router: CovidReportRouter
) : CovidReportPresenter {

    override fun viewReady() {

    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onSendButtonClick() {
        view.getReportCode()
        view.finish()
        router.navigateToConfirmation()
    }

}