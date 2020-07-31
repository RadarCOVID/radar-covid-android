package es.gob.radarcovid.features.covidreport.form.presenter

import android.util.Log
import es.gob.radarcovid.datamanager.usecase.GetInternetInfoUseCase
import es.gob.radarcovid.datamanager.usecase.ReportInfectedUseCase
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportRouter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
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
        reportInfectedUseCase.reportInfected(reportCode)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    view.hideLoading()
                    view.finish()
                    router.navigateToConfirmation()
                }, {
                    Log.e( "CovidReportPresenter", "Error reporting infected", it)
                    view.hideLoading()
                    view.showReportErrorDialog()
            })
    }

}