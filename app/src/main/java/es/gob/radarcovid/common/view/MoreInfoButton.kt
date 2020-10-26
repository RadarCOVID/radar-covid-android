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
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import kotlinx.android.synthetic.main.view_more_info_button.view.*
import javax.inject.Inject

class MoreInfoButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var labelManager: LabelManager

    private var labelId: String?

    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_more_info_button, this)

        context.obtainStyledAttributes(attrs, R.styleable.MoreInfoButton).apply {
            try {
                labelId = getString(R.styleable.MoreInfoButton_labelId)
                val defaultText = getText(R.styleable.MoreInfoButton_android_text) ?: ""
                textViewMoreInfo.setText(labelId, defaultText)
                textViewMoreInfo.setIsLink()
                textViewMoreInfo.removeUnderlineLink()
            } finally {
                recycle()
            }
        }
    }
}