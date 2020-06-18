package es.gob.covidradar.features.covidreport.form.protocols

import es.gob.covidradar.common.view.RequestView

interface CovidReportView : RequestView {

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