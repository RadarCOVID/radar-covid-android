package es.gob.covidradar.features.poll.question.protocols

import es.gob.covidradar.common.view.viewmodel.QuestionViewModel

interface QuestionView {

    fun showQuestion(question: QuestionViewModel)

    fun showButtonFinish()

    fun notifyButtonNextClick(answers: QuestionViewModel)

    fun getCurrentQuestion(): QuestionViewModel

}

interface QuestionPresenter {

    fun viewReady(isLastQuestion: Boolean, question: QuestionViewModel)

    fun onNextButtonClick()

}