package com.indra.coronaradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.indra.coronaradar.R
import kotlinx.android.synthetic.main.view_step_indicator.view.*

class StepIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_step_indicator, this)
    }

    fun setProgress(currentStep: Int, totalSteps: Int) {
        val currentProgress = if (currentStep == totalSteps)
            100
        else
            (100 / totalSteps) * currentStep

        progressBar.progress = currentProgress

        textViewStep.text = context.getString(R.string.step_indicator_text, currentStep, totalSteps)
    }
}