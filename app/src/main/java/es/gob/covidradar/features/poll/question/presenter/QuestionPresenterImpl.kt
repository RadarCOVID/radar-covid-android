package es.gob.covidradar.features.poll.question.presenter

import es.gob.covidradar.common.viewmodel.QuestionViewModel
import es.gob.covidradar.features.poll.question.protocols.QuestionPresenter
import es.gob.covidradar.features.poll.question.protocols.QuestionView
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