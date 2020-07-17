package es.gob.radarcovid.common.view

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.view.viewmodel.QuestionViewModel
import es.gob.radarcovid.datamanager.utils.LabelManager
import kotlinx.android.synthetic.main.view_question_edit_text.view.*
import javax.inject.Inject


class QuestionEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), AnswerView {

    @Inject
    lateinit var labelManager: LabelManager

    var question: QuestionViewModel? = null
        set(value) {
            field = value
            value?.let {
                editTextQuestion.setText(it.answers[0].text)
                editTextQuestion.filters = arrayOf(LengthFilter(it.maxValue))
            }
        }

    init {
        (context.applicationContext as HasAndroidInjector).androidInjector().inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_question_edit_text, this)
        editTextQuestion.addTextChangedListener { newText ->
            question?.let {
                with(it.answers[0]) {
                    text = newText.toString()
                    isSelected = text.isNotEmpty()
                }
            }
        }
        editTextQuestion.hint =
            labelManager.getText("POLL_TEXTAREA_PLACEHOLDER", R.string.poll_edit_text_hint)
    }

    override fun getSelectedAnswers(): QuestionViewModel = question ?: QuestionViewModel()
}