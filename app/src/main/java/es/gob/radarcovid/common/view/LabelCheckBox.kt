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
import androidx.appcompat.widget.AppCompatCheckBox
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

class LabelCheckBox : AppCompatCheckBox {

    @Inject
    lateinit var labelManager: LabelManager
    private var contentDescriptionLabelId: String? = null

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
                R.styleable.LabelCheckBox,
                0, 0
            ).apply {
                try {
                    contentDescriptionLabelId =
                        getString(R.styleable.LabelCheckBox_contentDescriptionLabelId)
                    val defaultText = getText(R.styleable.LabelCheckBox_android_contentDescription)
                    contentDescription =
                        labelManager.getText(contentDescriptionLabelId, defaultText)
                } finally {
                    recycle()
                }
            }
        }
    }

}