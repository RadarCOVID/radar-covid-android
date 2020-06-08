package com.indra.contacttracing.features.covidreport.presenter

import com.indra.contacttracing.features.covidreport.protocols.CovidReportPresenter
import com.indra.contacttracing.features.covidreport.protocols.CovidReportView
import javax.inject.Inject

class CovidReportPresenterImpl @Inject constructor(private val view: CovidReportView) :
    CovidReportPresenter {

    override fun viewReady() {

    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

}