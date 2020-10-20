/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_legal_terms.*

class LegalTermsDialog(context: Context) : LayoutContainer {

    private val dialog: Dialog

    override val containerView: View =
        LayoutInflater.from(context).inflate(R.layout.dialog_legal_terms, null)

    init {
        initializeInjector(context)

        dialog = AlertDialog.Builder(context)
            .setView(containerView)
            .setCancelable(false)
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        buttonOk.setOnClickListener { dialog.dismiss() }

        checkBoxTermsAndConditions.setOnClickListener {
            enableDisableButton()
        }

        checkBoxUsageConditions.setOnClickListener {
            enableDisableButton()
        }

    }

    private fun enableDisableButton() {
        buttonOk.isEnabled = checkBoxTermsAndConditions.isChecked && checkBoxUsageConditions.isChecked
    }

    fun show(onPositiveButtonClick: ((Dialog) -> Unit)) {
        buttonOk.setOnClickListener {
            dialog.dismiss()
            onPositiveButtonClick(dialog)
        }
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun initializeInjector(context: Context) {
        (context.applicationContext as? HasAndroidInjector)?.androidInjector()?.inject(this)
    }

}