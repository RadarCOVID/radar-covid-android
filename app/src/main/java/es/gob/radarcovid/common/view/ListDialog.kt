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

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import es.gob.radarcovid.R
import es.gob.radarcovid.common.view.adapter.SingleChoiceAdapter
import kotlinx.android.synthetic.main.list_dialog.*

class ListDialog(context: Context, view: View) : Dialog(context) {
    init {
        setContentView(view)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun initList(
        items: List<String>,
        selectedItemIndex: Int,
        onItemClickListener: (Int) -> Unit
    ) {
        listView.adapter =
            SingleChoiceAdapter(context, items, selectedItemIndex) { position ->
                onItemClickListener(position)
            }
    }

    class Builder(context: Context) {

        private val view: View =
            LayoutInflater.from(context).inflate(R.layout.list_dialog, null)
        private val textViewTitle = view.findViewById<LabelTextView>(R.id.textViewDialogTitle)
        private val buttonOk = view.findViewById<Button>(R.id.buttonOk)
        private val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
        private val listDialog: ListDialog = ListDialog(context, view)

        fun setPositiveButton(
            text: String,
            onPositiveButtonClick: ((Dialog) -> Unit)
        ): Builder {
            buttonOk.visibility = View.VISIBLE
            buttonOk.text = text
            buttonOk.setOnClickListener {
                onPositiveButtonClick(listDialog)
            }
            return this
        }

        fun setNegativeButton(
            text: String,
            onNegativeButtonClick: ((Dialog) -> Unit)
        ): Builder {
            buttonCancel.visibility = View.VISIBLE
            buttonCancel.text = text
            buttonCancel.setOnClickListener {
                onNegativeButtonClick(listDialog)
            }
            return this
        }

        fun setTitle(title: String): Builder {
            this.textViewTitle.visibility = View.VISIBLE
            this.textViewTitle.text = title
            return this
        }

        fun setItems(
            items: List<String>,
            selectedItemIndex: Int,
            onItemClickListener: (Int) -> Unit
        ): Builder {
            listDialog.initList(items, selectedItemIndex, onItemClickListener)
            return this
        }

        fun build(): ListDialog = listDialog

    }

}