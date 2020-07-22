package es.gob.radarcovid.features.covidreport.form.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import kotlinx.android.synthetic.main.activity_covid_report.*
import org.dpppt.android.sdk.DP3T
import javax.inject.Inject

class CovidReportActivity : BaseBackNavigationActivity(), CovidReportView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, CovidReportActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: CovidReportPresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DP3T.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_report)

        initViews()
        presenter.viewReady()
    }

    override fun onPause() {
        super.onPause()
        hideKeyBoard()
    }

    private fun initViews() {
        buttonSend.setOnClickListener {
            hideKeyBoard()
            presenter.onSendButtonClick()
        }
        codeEditText.textChangedListener = {
            presenter.onCodeChanged(it)
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onBackArrowClick(view: View) {
        hideKeyBoard()
        presenter.onBackPressed()
    }

    override fun showExitConfirmationDialog() {
        CMDialog.Builder(this)
            .setTitle(
                labelManager.getText(
                    "ALERT_MY_HEALTH_SEND_TITLE",
                    R.string.covid_report_abort_warning_title
                ).toString()
            )
            .setMessage(
                labelManager.getText(
                    "ALERT_MY_HEALTH_SEND_CONTENT",
                    R.string.covid_report_abort_warning_message
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.covid_report_abort_warning_button
                ).toString()
            ) {
                it.dismiss()
                presenter.onExitConfirmed()
            }
            .setCloseButton { it.dismiss() }
            .build()
            .show()
    }

    override fun getReportCode(): String = codeEditText.getText()

    override fun setButtonSendEnabled(enabled: Boolean) {
        buttonSend.isEnabled = enabled
    }

    override fun showNetworkWarningDialog() {
        CMDialog.createDialog(
            this,
            title = R.string.network_warning_dialog_title,
            description = R.string.network_warning_dialog_message,
            buttonText = R.string.network_warning_dialog_button,
            onCloseButtonClick = null
        ) {
            presenter.onRetryButtonClick()
        }.show()
    }

    override fun showReportErrorDialog() {
        showError(
            Exception(
                labelManager.getText(
                    "ALERT_MY_HEALTH_CODE_ERROR_CONTENT",
                    R.string.covid_report_error
                ).toString()
            )
        )
    }

}