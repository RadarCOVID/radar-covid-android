package com.indra.coronaradar.features.splash.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseActivity
import com.indra.coronaradar.common.view.CMDialog
import com.indra.coronaradar.features.splash.protocols.SplashPresenter
import com.indra.coronaradar.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    private var currentDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.viewReady()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showNoInternetWarning() {
        currentDialog?.let {
            if (it.isShowing)
                it.dismiss()
        }
        currentDialog = CMDialog.createDialog(
            this,
            R.string.warning_connection_title,
            R.string.warning_connection_description, R.string.warning_connection_button,
            onCloseButtonClick = {
                presenter.onNetworkDialogCloseButtonClick()
            },
            onButtonClick = {
                presenter.onNetworkRetryButtonClick()
            }
        )
        currentDialog?.show()
    }

    override fun showPlayServicesRequiredDialog() {
        currentDialog?.let {
            if (it.isShowing)
                it.dismiss()
        }
        currentDialog = AlertDialog.Builder(this)
            .setTitle(R.string.play_services_dialog_title)
            .setMessage(R.string.play_services_dialog_message)
            .setPositiveButton(
                R.string.accept
            ) { _, _ -> presenter.onInstallPlayServicesButtonClick() }
            .setCancelable(false)
            .create()
        currentDialog?.show()
    }

}
