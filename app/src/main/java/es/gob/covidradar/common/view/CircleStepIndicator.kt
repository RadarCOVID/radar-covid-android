package es.gob.covidradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import es.gob.covidradar.R

class CircleStepIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        var currentStep = 0
        var totalSteps = 0
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleStepIndicator,
            0, 0
        ).apply {

            try {
                currentStep = getInt(R.styleable.CircleStepIndicator_currentStep, 0)
                totalSteps = getInt(R.styleable.CircleStepIndicator_maxSteps, 0)
            } finally {
                recycle()
            }
        }

        setStep(currentStep, totalSteps)

    }

    fun setStep(currentStep: Int, maxSteps: Int) {
        removeAllViews()
        for (x in 0 until maxSteps) {
            val circle = ImageView(context)
            circle.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f)
            if (x == currentStep - 1)
                circle.setImageResource(R.drawable.ic_ellipse_selected)
            else
                circle.setImageResource(R.drawable.ic_ellipse)
            addView(circle)
        }
    }

}