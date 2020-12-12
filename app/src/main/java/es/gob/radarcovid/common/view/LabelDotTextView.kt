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
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import kotlinx.android.synthetic.main.view_dotted_textview.view.*
import javax.inject.Inject


class LabelDotTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var labelManager: LabelManager

    private var labelId: String?

    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)

        orientation = HORIZONTAL
        LayoutInflater.from(context)
            .inflate(R.layout.view_dotted_textview, this)
        context.obtainStyledAttributes(attrs, R.styleable.LabelDotTextView).apply {
            try {
                getDimension(R.styleable.LabelDotTextView_android_textSize, 0f)
                    .let { textSizeInPixels ->
                        if (textSizeInPixels > 0)
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPixels)
                    }

                labelId = getString(R.styleable.LabelDotTextView_labelId)
                val defaultText = getText(R.styleable.LabelDotTextView_android_text)
                when (getInt(R.styleable.LabelDotTextView_dotVisibility, 0)) {
                    0 -> imageViewDot.visibility = View.VISIBLE
                    1 -> imageViewDot.visibility = View.INVISIBLE
                    2 -> imageViewDot.visibility = View.GONE
                }
                textView.text = labelManager.getText(labelId, defaultText)

                val contentDescriptionLabelId =
                    getString(R.styleable.LabelDotTextView_contentDescriptionLabelId)
                val defaultTextContent =
                    getText(R.styleable.LabelDotTextView_contentDescription) ?: ""
                val customContentDescription =
                    labelManager.getText(contentDescriptionLabelId, defaultTextContent).toString()
                if (!customContentDescription.isNullOrEmpty()) {
                    textView.setCustomContentDescription(customContentDescription)
                }

                val isLink = getBoolean(R.styleable.LabelDotTextView_isLink, false)
                if (isLink) textView.setIsLink()

            } finally {
                recycle()
            }
        }
    }

    fun setText(labelId: String?, resId: Int) {
        setText(labelId, context.getString(resId))
    }

    fun setText(labelId: String?, text: CharSequence, type: TextView.BufferType) {
        textView.setText(labelManager.getText(labelId, text), type)
    }

    fun setText(labelId: String?, defaultText: CharSequence) {
        textView.text = labelManager.getText(labelId, defaultText)
    }

    fun reloadText() {
        textView.text = labelManager.getText(labelId, textView.text)
    }
}