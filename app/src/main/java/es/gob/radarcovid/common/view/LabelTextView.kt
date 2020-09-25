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
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.extensions.removeUnderline
import es.gob.radarcovid.datamanager.utils.LabelManager
import java.util.*
import javax.inject.Inject

class LabelTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @Inject
    lateinit var labelManager: LabelManager

    private var labelId: String?

    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LabelTextView,
            0, 0
        ).apply {
            try {
                labelId = getString(R.styleable.LabelTextView_labelId)
                val defaultText = getText(R.styleable.LabelTextView_android_text) ?: ""
                text = labelManager.getText(labelId, defaultText)

                val actionDescriptionLabelId =
                    getString(R.styleable.LabelTextView_actionDescriptionLabelId)
                val defaultTextAction = getText(R.styleable.LabelTextView_actionDescription) ?: ""
                val actionDescription =
                    labelManager.getText(actionDescriptionLabelId, defaultTextAction).toString()
                if (!actionDescription.isNullOrEmpty()) {
                    setAccessibilityAction(actionDescription!!)
                }

                val isHeading = getBoolean(R.styleable.LabelTextView_isHeading, false)
                setIsHeading(isHeading)

                val isLink = getBoolean(R.styleable.LabelTextView_isLink, false)
                val removeUnderlineLink =
                    getBoolean(R.styleable.LabelTextView_removeUnderlineLink, false)
                if (isLink) {
                    setIsLink()
                    if (removeUnderlineLink) removeUnderlineLink()
                }

            } finally {
                recycle()
            }
        }

    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        text?.let {
            contentDescription = it.toString().toLowerCase(Locale.ROOT)
        }
    }

    fun setText(labelId: String?, resId: Int) {
        setText(labelId, context.getString(resId))
    }

    fun setText(labelId: String?, defaultText: CharSequence) {
        text = labelManager.getText(labelId, defaultText)
    }

    fun setIsLink() {
        movementMethod = LinkMovementMethod.getInstance()
    }

    fun removeUnderlineLink() {
        text = text.removeUnderline()
    }

    fun reloadText() {
        text = labelManager.getText(labelId, text)
    }

    private fun setIsHeading(isHeading: Boolean) {
        ViewCompat.setAccessibilityHeading(this, isHeading)
    }

    private fun setAccessibilityAction(action: String) {
        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                // A custom action description. For example, you could use "pause"
                // to have TalkBack speak "double-tap to pause."
                val customClick = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_CLICK, action
                )
                info.addAction(customClick)
            }
        })
    }

}