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
        view.notifyButtonNextClick(view.getCurrentQuestion())
    }

//    private fun validate(question: QuestionViewModel): Boolean {
//
//        when(question.type) {
//            QuestionViewModel.Type.RATE -> TODO()
//            QuestionViewModel.Type.SINGLE_SELECTION -> TODO()
//            QuestionViewModel.Type.MULTIPLE_SELECTION -> TODO()
//            QuestionViewModel.Type.FIELD -> {
//
//            }
//        }
//        return true
//    }

}