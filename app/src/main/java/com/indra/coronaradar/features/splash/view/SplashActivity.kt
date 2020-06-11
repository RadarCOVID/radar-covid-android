package com.indra.coronaradar.features.splash.view

import android.os.Bundle
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseActivity
import com.indra.coronaradar.common.view.CMDialog
import com.indra.coronaradar.features.splash.protocols.SplashPresenter
import com.indra.coronaradar.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.viewReady()
    }

    override fun showNoInternetWarning() {
        CMDialog.createDialog(
            this,
            R.string.warning_connection_title,
            R.string.warning_connection_description, R.string.warning_connection_button,
            onCloseButtonClick = {
                presenter.onNetworkDialogCloseButtonClick()
            },
            onButtonClick = {
                presenter.onNetworkRetryButtonClick()
            }
        ).show()
    }
}
