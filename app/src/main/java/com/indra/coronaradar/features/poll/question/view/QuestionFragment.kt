package com.indra.coronaradar.features.poll.question.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseFragment
import com.indra.coronaradar.common.view.AnswerView
import com.indra.coronaradar.common.view.MultipleChoiceView
import com.indra.coronaradar.common.view.RateView
import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import com.indra.coronaradar.features.poll.question.protocols.QuestionPresenter
import com.indra.coronaradar.features.poll.question.protocols.QuestionView
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
                it.getParcelable(ARG_QUESTION) ?: QuestionViewModel.RateQuestion()
            )
        }
        initViews()
    }

    private fun initViews() {
        buttonNext.setOnClickListener { presenter.onNextButtonClick() }
    }

    override fun showQuestion(question: QuestionViewModel) {
        when (question) {
            is QuestionViewModel.RateQuestion -> {
                wrapperQuestion.addView(RateView(context!!).apply {
                    this.question = question
                })
                textViewQuestion.text = question.text
            }
            is QuestionViewModel.MultipleChoiceQuestion -> {
                wrapperQuestion.addView(MultipleChoiceView(context!!).apply {
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

    override fun getSelectedAnswers(): QuestionViewModel =
        (wrapperQuestion.getChildAt(0) as AnswerView).getSelectedAnswers()

    interface Callback {

        fun onContinueButtonClick(answers: QuestionViewModel)

    }

}