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
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.toSpanned
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.common.extensions.removeUnderline
import es.gob.radarcovid.datamanager.utils.LabelManager
import java.util.*
import javax.inject.Inject

class LabelTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @Inject
    lateinit var labelManager: LabelManager

    @Inject
    lateinit var navigationUtils: NavigationUtils

    private var labelId: String?

    private var customContentDescription: String?

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
                    setAccessibilityAction(actionDescription)
                }

                val contentDescriptionLabelId =
                    getString(R.styleable.LabelTextView_contentDescriptionLabelId)
                val defaultTextContent =
                    getText(R.styleable.LabelTextView_android_contentDescription) ?: ""
                customContentDescription =
                    labelManager.getText(contentDescriptionLabelId, defaultTextContent).toString()
                if (!customContentDescription.isNullOrEmpty()) {
                    setContentDescription(customContentDescription!!)
                }


                val isHeading = getBoolean(R.styleable.LabelTextView_isHeading, false)
                setIsHeading(isHeading)

                val isLink = getBoolean(R.styleable.LabelTextView_isLink, false)
                val removeUnderlineLink =
                    getBoolean(R.styleable.LabelTextView_removeUnderlineLink, false)
                if (isLink)
                    setIsLink()
                if (removeUnderlineLink)
                    removeUnderlineLink()

            } finally {
                recycle()
            }
        }

    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        text?.let {
            if (customContentDescription.isNullOrEmpty())
                contentDescription = it.toString().toLowerCase(Locale.ROOT)
        }
    }

    fun setText(labelId: String?, defaultText: CharSequence) {
        text = labelManager.getText(labelId, defaultText)
    }

    private fun getTextUrl(): String? {
        val spanned = text.toSpanned()
        val spans = spanned.getSpans(0, spanned.length, URLSpan::class.java)
        return if (!spans.isNullOrEmpty())
            (spans[0] as URLSpan).url
        else
            null
    }

    fun setIsLink() {
        movementMethod = LinkMovementMethod.getInstance()
        getTextUrl()?.let { url ->
            setOnClickListener {
                navigationUtils.navigateToBrowser(context, url)
            }
        }
    }

    fun setCustomContentDescription(customContentDescription: String) {
        this.customContentDescription = customContentDescription
        contentDescription = customContentDescription
    }

    fun removeUnderlineLink() {
        text = text.removeUnderline()
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

    private fun setContentDescription(desc: String) {
        contentDescription = desc
    }
}