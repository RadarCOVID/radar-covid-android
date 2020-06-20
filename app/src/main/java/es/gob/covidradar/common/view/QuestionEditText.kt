package es.gob.covidradar.common.view

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import es.gob.covidradar.R
import es.gob.covidradar.common.view.viewmodel.QuestionViewModel
import kotlinx.android.synthetic.main.view_question_edit_text.view.*


class QuestionEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), AnswerView {

    var question: QuestionViewModel? = null
        set(value) {
            field = value
            value?.let {
                editTextQuestion.setText(it.answers[0].text)
                editTextQuestion.filters = arrayOf(LengthFilter(it.maxValue))
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_question_edit_text, this)
        editTextQuestion.addTextChangedListener { newText ->
            question?.let {
                with(it.answers[0]) {
                    text = newText.toString()
                    isSelected = text.isNotEmpty()
                }
            }
        }
    }

    override fun getSelectedAnswers(): QuestionViewModel = question ?: QuestionViewModel()
}