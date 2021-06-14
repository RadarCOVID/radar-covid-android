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
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

class LabelConstraintLayout : ConstraintLayout {

    @Inject
    lateinit var labelManager: LabelManager

    private var customContentDescription: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, this)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, this)
    }

    private fun init(context: Context, attrs: AttributeSet?, view: View) {
        if (attrs != null) {
            (context.applicationContext as HasAndroidInjector)
                .androidInjector()
                .inject(this)
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LabelConstraintLayout,
                0, 0
            ).apply {
                try {
                    val actionDescriptionLabelId =
                        getString(R.styleable.LabelConstraintLayout_actionDescriptionLabelId)
                    val defaultActionDescription =
                        getText(R.styleable.LabelConstraintLayout_actionDescription) ?: ""
                    val actionDescription =
                        labelManager.getText(actionDescriptionLabelId, defaultActionDescription)
                            .toString()
                    if (actionDescription.isNotEmpty())
                        setAccessibilityAction(actionDescription)

                } finally {
                    recycle()
                }
            }
        }
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