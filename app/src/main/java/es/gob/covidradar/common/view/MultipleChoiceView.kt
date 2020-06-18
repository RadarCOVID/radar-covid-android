package es.gob.covidradar.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import es.gob.covidradar.R
import es.gob.covidradar.common.viewmodel.AnswerViewModel
import es.gob.covidradar.common.viewmodel.QuestionViewModel
import kotlinx.android.synthetic.main.view_multiple_choice_item.view.*

class MultipleChoiceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SelectableGroupLinearLayout(context, attrs, defStyleAttr), AnswerView {

    var question: QuestionViewModel? = null
        set(value) {
            field = value
            value?.let {
                allowMultipleSelection = it.type == QuestionViewModel.Type.MULTIPLE_SELECTION
                it.answers.forEach { answer ->
                    val multipleChoiceItemView = MultipleChoiceItemView(context)
                    addView(multipleChoiceItemView)
                    multipleChoiceItemView.answer = answer
                }
            }
        }

    init {

        orientation = VERTICAL

    }

    private class MultipleChoiceItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : LinearLayout(context, attrs, defStyleAttr) {

        var answer: AnswerViewModel? = null
            set(value) {
                field = value
                setText(field?.text ?: "")
                if (value?.isSelected == true)
                    (parent as? SelectableGroup)?.onChildSelected(this)
            }

        init {
            LayoutInflater.from(context).inflate(R.layout.view_multiple_choice_item, this)
            wrapperContent.setOnClickListener { (parent as? SelectableGroup)?.onChildSelected(this) }
        }

        override fun setSelected(selected: Boolean) {
            super.setSelected(selected)
            if (selected)
                imageViewCheck.visibility = View.VISIBLE
            else
                imageViewCheck.visibility = View.GONE

            answer?.isSelected = selected
        }

        private fun setText(text: String) {
            textViewAnswer.text = text
        }
    }

    override fun getSelectedAnswers(): QuestionViewModel =
        question ?: QuestionViewModel()

}