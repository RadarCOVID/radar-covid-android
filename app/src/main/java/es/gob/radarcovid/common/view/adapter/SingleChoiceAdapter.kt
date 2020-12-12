/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

class SingleChoiceAdapter(
    private val context: Context,
    private val items: List<String>,
    private var selectedItemIndex: Int,
    private val onItemClickListener: ((Int) -> Unit)
) : BaseAdapter() {

    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val isAccessibilityEnabled: Boolean =
        (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as android.view.accessibility.AccessibilityManager).isEnabled

    @Inject
    lateinit var labelManager: LabelManager

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val viewHolder: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val textView = viewHolder.findViewById<TextView>(R.id.textViewListItem)
        textView.text = getItem(position)

        if (position == selectedItemIndex) {
            textView.contentDescription =
                "${getItem(position)} ${labelManager.getText(
                    "ACC_SELECTED",
                    R.string.single_choice_selected
                )}"
            textView.setTypeface(textView.typeface, Typeface.BOLD)
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))
            if (isAccessibilityEnabled)
                textView.setBackgroundResource(R.color.purple_FF)
        } else {
            textView.contentDescription = getItem(position)
            textView.typeface = Typeface.create(textView.typeface, Typeface.NORMAL)
            textView.setTextColor(ContextCompat.getColor(context, R.color.purple_9C))
            if (isAccessibilityEnabled)
                textView.setBackgroundResource(android.R.color.transparent)
        }

        textView.setOnClickListener {
            selectedItemIndex = position
            textView.contentDescription =
                "${labelManager.getText("ACC_SELECTED", R.string.single_choice_selected)} ${getItem(
                    position
                )}"
            notifyDataSetChanged()
            onItemClickListener(position)
        }

        return viewHolder
    }

    override fun getItem(position: Int): String = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

}