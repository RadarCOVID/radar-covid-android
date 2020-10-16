/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import kotlinx.android.synthetic.main.layout_progress_steps.view.*
import javax.inject.Inject

class StepsProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var labelManager: LabelManager

    private var labelId: String?
    private var defaultText: CharSequence

    init {
        inflate(context, R.layout.layout_progress_steps, this)

        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)


        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StepsProgress,
            0, 0
        ).apply {
            try {

                labelId = getString(R.styleable.StepsProgress_labelId)
                defaultText = getText(R.styleable.StepsProgress_android_text) ?: ""

                val actualStep = getInteger(R.styleable.StepsProgress_actualStep, 0)
                val numberSteps = getInteger(R.styleable.StepsProgress_numberSteps, 2)

                setSteps(actualStep, numberSteps)

            } finally {
                recycle()
            }
        }
    }


    fun setSteps(actual: Int, total: Int) {
        progressBar.progress = actual
        progressBar.max = total
        setText()
    }

    private fun getActualStep(): String =
        progressBar.progress.toString()


    private fun getNumberSteps(): String =
        progressBar.max.toString()


    private fun setText() {
        val steps = labelManager.getText(labelId, defaultText).toString()
        val formatSteps = steps.replace("$1", getActualStep())
            .replace("$2", getNumberSteps())

        textSteps.text = formatSteps
    }
}