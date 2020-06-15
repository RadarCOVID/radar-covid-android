package com.indra.coronaradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.core.content.ContextCompat
import com.indra.coronaradar.R
import com.indra.coronaradar.common.viewmodel.AnswerViewModel
import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import kotlinx.android.synthetic.main.view_rate.view.*
import kotlinx.android.synthetic.main.view_rate_item.view.*

class RateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var question: QuestionViewModel.Rate? = null
        set(value) {
            field = value
            value?.let {
                it.answers.forEachIndexed { index, answerViewModel ->
                    val rateItem = RateItemView(context)
                    rateItem.answer = answerViewModel
                    when (index) {
                        0 -> rateItem.setBackgroundResource(R.drawable.selector_rate_view_start)
                        it.answers.size - 1 -> rateItem.setBackgroundResource(R.drawable.selector_rate_view_end)
                        else -> rateItem.setBackgroundResource(R.drawable.selector_rate_view_mid)
                    }
                    wrapperContent.addView(rateItem)
                }
            }
        }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_rate, this)


//        for (x in 1..10) {
//            val rateItem = when (x) {
//                1 -> RateItemView(context).apply {
//                    setBackgroundResource(R.drawable.selector_rate_view_start)
//                }
//                10 -> RateItemView(context).apply {
//                    setBackgroundResource(R.drawable.selector_rate_view_end)
//                }
//                else -> RateItemView(context).apply {
//                    setBackgroundResource(R.drawable.selector_rate_view_mid)
//                }
//            }
//            wrapperContent.addView(rateItem)
//        }
    }


    class RateItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr) {

        var answer: AnswerViewModel? = null
            set(value) {
                field = value
                setText(field?.text ?: "")
            }

        init {
            LayoutInflater.from(context).inflate(R.layout.view_rate_item, this)
            layoutParams = LayoutParams(
                0,
                context.resources.getDimensionPixelSize(R.dimen.rate_item_height),
                1.0f
            ).apply { gravity = Gravity.CENTER }


            setBackgroundResource(R.drawable.selector_rate_view_mid)

            setOnClickListener { (parent as? SelectableGroup)?.onChildSelected(this) }

        }

        private fun setText(text: String) {
            textViewRate.text = text
        }

        override fun setSelected(selected: Boolean) {
            super.setSelected(selected)
            if (selected)
                textViewRate.setTextColor(ContextCompat.getColor(context, R.color.white))
            else
                textViewRate.setTextColor(ContextCompat.getColor(context, R.color.black_26))
        }

    }

}