package es.gob.covidradar.common.base

import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import es.gob.covidradar.R
import es.gob.covidradar.common.view.TransparentProgressDialog
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    private var progressBar: TransparentProgressDialog? = null

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
        progressBar?.hide()
    }

    fun showError(error: Throwable, finishOnDismiss: Boolean = false) {
        showError(
            title = null,
            message = error.message ?: getString(R.string.error_generic),
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