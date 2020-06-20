package es.gob.covidradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout.LayoutParams
import androidx.core.content.ContextCompat
import es.gob.covidradar.R
import es.gob.covidradar.common.view.viewmodel.AnswerViewModel
import es.gob.covidradar.common.view.viewmodel.QuestionViewModel
import kotlinx.android.synthetic.main.view_rate_item.view.*

class RateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SelectableGroupLinearLayout(context, attrs, defStyleAttr), AnswerView {

    var question: QuestionViewModel? = null
        set(value) {
            field = value
            value?.let {
                it.answers.forEachIndexed { index, answer ->
                    val rateItem = RateItemView(context)
                    when (index) {
                        0 -> rateItem.setBackgroundResource(R.drawable.selector_rate_view_start)
                        it.answers.size - 1 -> rateItem.setBackgroundResource(R.drawable.selector_rate_view_end)
                        else -> rateItem.setBackgroundResource(R.drawable.selector_rate_view_mid)
                    }
                    addView(rateItem)
                    rateItem.answer = answer
                }
            }
        }

    init {
        orientation = HORIZONTAL
    }


    private class RateItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr) {

        var answer: AnswerViewModel? = null
            set(value) {
                field = value
                setText(field?.text ?: "")
                if (value?.isSelected == true)
                    (parent as? SelectableGroup)?.onChildSelected(this)
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
            answer?.isSelected = selected
        }

    }

    override fun getSelectedAnswers(): QuestionViewModel = question ?: QuestionViewModel()

}