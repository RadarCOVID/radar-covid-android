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
import androidx.appcompat.widget.AppCompatRadioButton
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

class SegmentedControlLabelButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatRadioButton(context, attrs, R.attr.segmentedControlButtonStyle) {

    @Inject
    lateinit var labelManager: LabelManager

    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedControlLabelButton,
            0, 0
        ).apply {

            try {
                val labelId = getString(R.styleable.SegmentedControlLabelButton_labelId)
                val defaultText =
                    getText(R.styleable.SegmentedControlLabelButton_android_text) ?: ""
                text = labelManager.getText(labelId, defaultText)

                val contentDescriptionLabelId =
                    getString(R.styleable.SegmentedControlLabelButton_contentDescriptionLabelId)
                val defaultTextContent =
                    getText(R.styleable.SegmentedControlLabelButton_android_contentDescription)
                        ?: ""
                val customContentDescription =
                    labelManager.getText(contentDescriptionLabelId, defaultTextContent).toString()
                if (!customContentDescription.isNullOrEmpty()) {
                    contentDescription = customContentDescription
                }

            } finally {
                recycle()
            }
        }
    }
}
