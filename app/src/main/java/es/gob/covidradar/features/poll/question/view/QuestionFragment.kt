package es.gob.covidradar.features.poll.question.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.covidradar.R
import es.gob.covidradar.common.base.BaseFragment
import es.gob.covidradar.common.view.AnswerView
import es.gob.covidradar.common.view.MultipleChoiceView
import es.gob.covidradar.common.view.QuestionEditText
import es.gob.covidradar.common.view.RateView
import es.gob.covidradar.common.view.viewmodel.QuestionViewModel
import es.gob.covidradar.features.poll.question.protocols.QuestionPresenter
import es.gob.covidradar.features.poll.question.protocols.QuestionView
import kotlinx.android.synthetic.main.fragment_question.*
import javax.inject.Inject

class QuestionFragment : BaseFragment(), QuestionView {

    companion object {

        const val TAG = "QuestionFragment"

        private const val ARG_QUESTION = "arg_question"
        private const val ARG_IS_LAST_QUESTION = "arg_is_last_question"

        fun newInstance(isLastQuestion: Boolean, question: QuestionViewModel) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_LAST_QUESTION, isLastQuestion)
                    putParcelable(ARG_QUESTION, question)
                }
            }

    }

    @Inject
    lateinit var presenter: QuestionPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            presenter.viewReady(
                it.getBoolean(ARG_IS_LAST_QUESTION, false),
                it.getParcelable(ARG_QUESTION) ?: QuestionViewModel()
            )
        }
        initViews()
    }

    private fun initViews() {
        buttonNext.setOnClickListener { presenter.onNextButtonClick() }
    }

    override fun showQuestion(question: QuestionViewModel) {
        when (question.type) {
            QuestionViewModel.Type.RATE -> {
                wrapperQuestion.addView(RateView(context!!).apply {
                    this.question = question
                })
                textViewQuestion.text = question.text
            }
            QuestionViewModel.Type.SINGLE_SELECTION,
            QuestionViewModel.Type.MULTIPLE_SELECTION -> {
                wrapperQuestion.addView(MultipleChoiceView(context!!).apply {
                    this.question = question
                })
                textViewQuestion.text = question.text
            }
            QuestionViewModel.Type.FIELD -> {
                wrapperQuestion.addView(QuestionEditText(context!!).apply {
                    this.question = question
                })
                textViewQuestion.text = question.text
            }
        }
    }

    override fun showButtonFinish() {
        buttonNext.setText(R.string.poll_button_finish)
    }

    override fun notifyButtonNextClick(answers: QuestionViewModel) {
        (activity as? Callback)?.onContinueButtonClick(answers)
    }

    override fun getCurrentQuestion(): QuestionViewModel =
        (wrapperQuestion.getChildAt(0) as AnswerView).getSelectedAnswers()

    interface Callback {

        fun onContinueButtonClick(answers: QuestionViewModel)

    }

}