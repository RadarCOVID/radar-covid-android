package es.gob.radarcovid.features.covidreport.form.presenter

import es.gob.radarcovid.datamanager.usecase.GetInternetInfoUseCase
import es.gob.radarcovid.datamanager.usecase.ReportInfectedUseCase
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportRouter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import javax.inject.Inject

class CovidReportPresenterImpl @Inject constructor(
    private val view: CovidReportView,
    private val router: CovidReportRouter,
    private val reportInfectedUseCase: ReportInfectedUseCase,
    private val getInternetInfoUseCase: GetInternetInfoUseCase
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
        if (getInternetInfoUseCase.isInternetAvailable()) {
            reportInfected(view.getReportCode())
        } else {
            view.showNetworkWarningDialog()
        }
    }

    private fun reportInfected(reportCode: String) {
        view.showLoading()
        reportInfectedUseCase.reportInfected(reportCode,
            onSuccess = {
                view.hideLoading()
                view.finish()
                router.navigateToConfirmation()
            },
            onError = {
                view.hideLoading()
                //view.showError(it)
                view.showReportErrorDialog()
            }
        )
    }

}