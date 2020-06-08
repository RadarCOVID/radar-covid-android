package com.indra.contacttracing.features.covidreport.protocols

interface CovidReportView {

    fun showExitConfirmationDialog()

    fun getReportCode(): String

    fun finish()

}

interface CovidReportPresenter {

    fun viewReady()

    fun onBackPressed()

    fun onExitConfirmed()

    fun onSendButtonClick()


}