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

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import es.gob.radarcovid.R
import es.gob.radarcovid.common.extensions.setSafeOnClickListener

object CommonDialog {

    class Builder(private val context: Context) {

        private val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_common, null)
        private val textViewTitle = view.findViewById<LabelTextView>(R.id.textViewDialogTitle)
        private val textViewMessage =
            view.findViewById<LabelTextView>(R.id.textViewMessage)
        private val buttonClose = view.findViewById<LabelImageButton>(R.id.buttonClose)
        private val buttonOk = view.findViewById<LabelButton>(R.id.buttonOk)
        private val buttonCancel = view.findViewById<LabelButton>(R.id.buttonCancel)
        private val dialog: AlertDialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        fun setTitle(resId: Int): Builder = setTitle(context.getString(resId))

        fun setTitle(text: String): Builder {
            textViewTitle.visibility = View.VISIBLE
            textViewTitle.text = text
            return this
        }

        fun setMessage(resId: Int): Builder = setMessage(context.getString(resId))

        fun setMessage(text: String): Builder {
            textViewMessage.visibility = View.VISIBLE
            textViewMessage.text = text
            return this
        }

        fun setPositiveButton(
            resId: Int,
            onPositiveButtonClick: ((AlertDialog) -> Unit)
        ): Builder = setPositiveButton(context.getString(resId), onPositiveButtonClick)

        fun setPositiveButton(
            text: String,
            onPositiveButtonClick: ((AlertDialog) -> Unit)
        ): Builder {
            buttonOk.visibility = View.VISIBLE
            buttonOk.text = text
            buttonOk.setSafeOnClickListener {
                onPositiveButtonClick(dialog)
            }
            return this
        }

        fun setNegativeButton(
            resId: Int,
            onNegativeButtonClick: ((AlertDialog) -> Unit)
        ): Builder = setNegativeButton(context.getString(resId), onNegativeButtonClick)

        fun setNegativeButton(
            text: String,
            onNegativeButtonClick: ((AlertDialog) -> Unit)
        ): Builder {
            buttonCancel.visibility = View.VISIBLE
            buttonCancel.text = text
            buttonCancel.setSafeOnClickListener {
                onNegativeButtonClick(dialog)
            }
            return this
        }

        fun setCloseButton(
            onCloseButtonClick: ((AlertDialog) -> Unit)
        ): Builder {
            buttonClose.setSafeOnClickListener {
                onCloseButtonClick(dialog)
            }
            return this
        }

        fun build(): AlertDialog = dialog

    }
}