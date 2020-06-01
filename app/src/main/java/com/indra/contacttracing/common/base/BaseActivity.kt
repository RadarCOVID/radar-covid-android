package com.indra.contacttracing.common.base

import android.view.LayoutInflater
import android.widget.TextView
import com.indra.contacttracing.R
import com.indra.contacttracing.common.view.TransparentProgressDialog
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    private var progressBar: TransparentProgressDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        progressBar?.dismiss()
    }

    fun showLoading() {
        if (progressBar == null)
            progressBar = TransparentProgressDialog(this)
        progressBar?.setCanceledOnTouchOutside(false)
        progressBar?.setCancelable(false)
        progressBar?.let {
            if(!it.isShowing)
                it.show()
        }
    }

    fun hideLoading() {
        progressBar?.hide()
    }

    fun showError(error: Throwable) {
        showError(message = error.message ?: getString(R.string.error_generic))
    }

    private fun showError(title: String? = null, message: String) {
        if (!isFinishing) {
            val builder: androidx.appcompat.app.AlertDialog.Builder =
                androidx.appcompat.app.AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_error, null)
            view.findViewById<TextView>(R.id.textViewErrorMessage).text = message
            builder.setView(view)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog: androidx.appcompat.app.AlertDialog = builder.create()
            dialog.show()
        }
    }

}