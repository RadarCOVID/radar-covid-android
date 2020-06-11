package com.indra.coronaradar.features.covidreport.form.protocols

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

interface CovidReportRouter {

    fun navigateToConfirmation()

}