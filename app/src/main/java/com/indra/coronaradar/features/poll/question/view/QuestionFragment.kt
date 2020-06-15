package com.indra.coronaradar.features.poll.question.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseFragment
import com.indra.coronaradar.common.view.MultipleChoiceView
import com.indra.coronaradar.common.view.RateView
import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import com.indra.coronaradar.features.poll.question.protocols.QuestionPresenter
import com.indra.coronaradar.features.poll.question.protocols.QuestionView
import kotlinx.android.synthetic.main.fragment_question.*
import javax.inject.Inject

class QuestionFragment : BaseFragment(), QuestionView {

    companion object {

        private const val ARG_QUESTION = "arg_question"

        fun newInstance(question: QuestionViewModel) = QuestionFragment().apply {
            arguments = Bundle().apply {
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
        presenter.viewReady(arguments?.getParcelable(ARG_QUESTION) ?: QuestionViewModel.Rate())
    }

    override fun showQuestion(question: QuestionViewModel) {
        when (question) {
            is QuestionViewModel.Rate -> {
                wrapperQuestion.addView(RateView(context!!).apply {
                    this.question = question
                })
                textViewQuestion.text = question.text
            }
            is QuestionViewModel.MultipleChoice -> {
                wrapperQuestion.addView(MultipleChoiceView(context!!).apply {
                    this.question = question
                })
                textViewQuestion.text = question.text
            }
        }
    }
}