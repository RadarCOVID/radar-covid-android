package es.gob.radarcovid.features.splash.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.splash.protocols.SplashPresenter
import es.gob.radarcovid.features.splash.protocols.SplashView
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
        currentDialog?.dismiss()
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
        currentDialog?.dismiss()
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

    override fun showNeedUpdateDialog() {
        currentDialog?.dismiss()
        currentDialog = CMDialog.createDialog(
            this,
            R.string.empty_text,
            R.string.warning_need_update_message, R.string.warning_need_update_button,
            onCloseButtonClick = {
                finish()
            },
            onButtonClick = {
                presenter.onUpdateAppButtonClick()
            }
        )
        currentDialog?.show()
    }

}
