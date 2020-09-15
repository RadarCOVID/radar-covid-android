/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerAppCompatActivity
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.R
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.common.view.LoadingDialog
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    private var progressBar: LoadingDialog? = null

    @Inject
    lateinit var labelManager: LabelManager

    override fun onDestroy() {
        super.onDestroy()
        progressBar?.dismiss()
    }

    fun showLoading() {
        progressBar?.dismiss()
        progressBar = LoadingDialog(this)
        progressBar?.setCanceledOnTouchOutside(false)
        progressBar?.setCancelable(false)
        progressBar?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideLoading() {
        progressBar?.dismissWithAnimation()
    }

    fun hideLoadingWithError(error: Throwable) {
        val title = if (error.message == null)
            labelManager.getText("ALERT_GENERIC_ERROR_TITLE", R.string.error_generic_title)
                .toString()
        else
            null
        val message = error.message ?: labelManager.getText(
            "ALERT_GENERIC_ERROR_CONTENT",
            R.string.error_generic_message
        ).toString()
        val button = labelManager.getText(
            "ALERT_ACCEPT_BUTTON",
            R.string.accept
        ).toString()

        progressBar?.showError(title, message, button)
    }

    fun hideLoadingWithError(
        title: String?,
        message: String?,
        button: String,
        onClick: (() -> Unit) = {}
    ) {
        progressBar?.showError(title, message, button, onClick)
    }

    fun showError(error: Throwable, finishOnDismiss: Boolean = false) {
        val title = if (error.message == null)
            labelManager.getText("ALERT_GENERIC_ERROR_TITLE", R.string.error_generic_title)
                .toString()
        else
            null
        val message = error.message ?: labelManager.getText(
            "ALERT_GENERIC_ERROR_CONTENT",
            R.string.error_generic_message
        ).toString()
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