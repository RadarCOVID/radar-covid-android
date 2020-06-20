package es.gob.covidradar.features.covidreport.form.presenter

import es.gob.covidradar.datamanager.usecase.ReportInfectedUseCase
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportRouter
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportView
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

    override fun onSendButtonClick() {
        view.getReportCode()
        reportInfected()
    }

    private fun reportInfected() {
        view.showLoading()
        reportInfectedUseCase.reportInfected(
            onSuccess = {
                view.hideLoading()
                view.finish()
                router.navigateToConfirmation()
            },
            onError = {
                view.hideLoading()
                view.showError(it)
            }
        )
    }

}