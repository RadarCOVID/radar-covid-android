package es.gob.radarcovid.common.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerAppCompatActivity
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.R
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.common.view.TransparentProgressDialog
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    private var progressBar: TransparentProgressDialog? = null

    @Inject
    lateinit var labelManager: LabelManager

    override fun onDestroy() {
        super.onDestroy()
        progressBar?.dismiss()
    }

    fun showLoading() {
        progressBar?.dismiss()
        progressBar = TransparentProgressDialog(this)
        progressBar?.setCanceledOnTouchOutside(false)
        progressBar?.setCancelable(false)
        progressBar?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideLoading() {
        progressBar?.hide()
    }

    fun showError(error: Throwable, finishOnDismiss: Boolean = false) {
        showError(
            title = if (error.message == null)
                labelManager.getText("ALERT_GENERIC_ERROR_TITLE", R.string.error_generic_title)
                    .toString()
            else
                null
            ,
            message = error.message ?: labelManager.getText(
                "ALERT_GENERIC_ERROR_CONTENT",
                R.string.error_generic_message
            ).toString(),
            finishOnDismiss = finishOnDismiss
        )
    }

    private fun showError(title: String? = null, message: String, finishOnDismiss: Boolean) {
        if (!isFinishing) {
            CMDialog.Builder(this)
                .apply {
                    if (title != null)
                        setTitle(title)
                }
                .setMessage(message)
                .setPositiveButton(
                    labelManager.getText(
                        "ALERT_ACCEPT_BUTTON",
                        R.string.accept
                    ).toString()
                ) {
                    it.dismiss()
                    if (finishOnDismiss)
                        finish()
                }
                .build()
                .show()
        }
    }

    fun hideKeyBoard() {
        try {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                currentFocus?.windowToken,
                0
            )
        } catch (e: Exception) {
            if (BuildConfig.DEBUG)
                e.printStackTrace()
        }
    }

}