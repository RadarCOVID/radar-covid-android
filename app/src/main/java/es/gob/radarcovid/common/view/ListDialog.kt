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
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import es.gob.radarcovid.R
import kotlinx.android.synthetic.main.list_dialog.*

class ListDialog(context: Context) : Dialog(context) {

    private var selectedIndex: Int = 0

    fun init(
        view: View,
        map: List<String>?,
        index: Int,
        listener: OnItemClickListener
    ) {
        setContentView(view)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        selectedIndex = index

        val adapter = object : ArrayAdapter<String>(context, R.layout.list_item, map!!) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById(R.id.textViewListItem) as TextView
                if (position == selectedIndex) {
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    textView.setBackgroundResource(R.color.purple)
                }
                else {
                    textView.typeface = Typeface.create(textView.typeface, Typeface.NORMAL)
                    textView.setBackgroundResource(android.R.color.transparent)
                }
                return view
            }
        }

        listView.adapter = adapter
        listView.visibility = View.VISIBLE
        listView.setItemChecked(index, true)
        listView.setOnItemClickListener { _, _, i, _ ->
            listener.onClickResult(i)
            selectedIndex = i
            adapter.notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun onClickResult(position: Int)
    }

    class Builder(context: Context) {

        private val view: View =
            LayoutInflater.from(context).inflate(R.layout.list_dialog, null)
        private val textViewTitle = view.findViewById<LabelTextView>(R.id.textViewDialogTitle)
        private val buttonOk = view.findViewById<Button>(R.id.buttonOk)
        private val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
        private var index: Int = 0
        private var listener: OnItemClickListener? = null
        private var list: List<String>? = null
        private val listDialog: ListDialog = ListDialog(context)

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

        fun setList(map: List<String>): Builder {
            this.list = map
            return this
        }

        fun setSelected(index: Int): Builder {
            this.index = index
            return this
        }

        fun setOnItemClick(listener: OnItemClickListener): Builder {
            this.listener = listener
            return this
        }

        fun show() = run {
            listDialog.init(view, list, index, listener!!)
            listDialog.show()
        }
    }

}