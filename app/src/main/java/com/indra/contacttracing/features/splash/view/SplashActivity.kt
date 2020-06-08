package com.indra.contacttracing.features.splash.view

import android.os.Bundle
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.common.view.CMDialog
import com.indra.contacttracing.features.splash.protocols.SplashPresenter
import com.indra.contacttracing.features.splash.protocols.SplashView
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
