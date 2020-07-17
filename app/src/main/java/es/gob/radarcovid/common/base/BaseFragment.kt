package es.gob.radarcovid.common.base

import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import dagger.android.support.DaggerFragment
import es.gob.radarcovid.R
import es.gob.radarcovid.common.view.TransparentProgressDialog
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    private var progressBar: TransparentProgressDialog? = null

    @Inject
    lateinit var labelManager: LabelManager

    override fun onDestroy() {
        super.onDestroy()
        progressBar?.dismiss()
    }

    fun showLoading() {
        hideLoading()
        progressBar = TransparentProgressDialog(context!!)
        progressBar?.setCanceledOnTouchOutside(false)
        progressBar?.setCancelable(false)
        progressBar?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideLoading() {
        progressBar?.dismiss()
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
        if (activity?.isFinishing == false) {
            val builder: AlertDialog.Builder =
                AlertDialog.Builder(context!!)
            val view = LayoutInflater.from(context!!).inflate(R.layout.dialog_error, null)
            view.findViewById<TextView>(R.id.textViewErrorMessage).text = message
            builder.setView(view)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                if (finishOnDismiss)
                    activity?.finish()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

}