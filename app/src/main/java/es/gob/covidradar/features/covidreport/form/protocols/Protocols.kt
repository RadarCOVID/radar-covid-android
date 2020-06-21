package es.gob.covidradar.features.covidreport.form.protocols

import es.gob.covidradar.common.view.RequestView

interface CovidReportView : RequestView {

    fun showExitConfirmationDialog()

    fun getReportCode(): String

    fun setButtonSendEnabled(enabled: Boolean)

    fun showNetworkWarningDialog()

    fun finish()

}

interface CovidReportPresenter {

    fun viewReady()

    fun onBackPressed()

    fun onExitConfirmed()

    fun onCodeChanged(code: String)

    fun onRetryButtonClick()

    fun onSendButtonClick()

}

interface CovidReportRouter {

    fun navigateToConfirmation()

}