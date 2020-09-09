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
import androidx.appcompat.widget.AppCompatEditText
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

class LabelEditText : AppCompatEditText {

    @Inject
    lateinit var labelManager: LabelManager

    private var hintLabelId: String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            (context.applicationContext as HasAndroidInjector)
                .androidInjector()
                .inject(this)
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LabelEditText,
                0, 0
            ).apply {
                try {
                    hintLabelId =
                        getString(R.styleable.LabelEditText_hintLabelId)
                    val defaultText = getText(R.styleable.LabelEditText_android_hint)
                    hint = labelManager.getText(hintLabelId, defaultText)
                } finally {
                    recycle()
                }
            }
        }
    }
    
}