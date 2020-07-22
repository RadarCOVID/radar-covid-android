package es.gob.radarcovid.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import dagger.android.support.DaggerAppCompatActivity
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.R
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
            val builder: androidx.appcompat.app.AlertDialog.Builder =
                androidx.appcompat.app.AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_error, null)
            view.findViewById<TextView>(R.id.textViewErrorMessage).text = message
            builder.setView(view)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                if (finishOnDismiss)
                    finish()
            }
            val dialog: androidx.appcompat.app.AlertDialog = builder.create()
            dialog.show()
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