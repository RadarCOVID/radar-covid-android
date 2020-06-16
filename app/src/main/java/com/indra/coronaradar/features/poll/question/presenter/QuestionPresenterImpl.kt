package com.indra.coronaradar.features.poll.question.presenter

import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import com.indra.coronaradar.features.poll.question.protocols.QuestionPresenter
import com.indra.coronaradar.features.poll.question.protocols.QuestionView
import javax.inject.Inject

class QuestionPresenterImpl @Inject constructor(private val view: QuestionView) :
    QuestionPresenter {

    override fun viewReady(isLastQuestion: Boolean, question: QuestionViewModel) {
        if (isLastQuestion)
            view.showButtonFinish()
        view.showQuestion(question)
    }

    override fun onNextButtonClick() {
        view.notifyButtonNextClick(view.getSelectedAnswers())
    }

}