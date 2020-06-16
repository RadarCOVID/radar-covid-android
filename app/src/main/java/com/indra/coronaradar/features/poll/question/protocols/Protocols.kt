package com.indra.coronaradar.features.poll.question.protocols

import com.indra.coronaradar.common.viewmodel.QuestionViewModel

interface QuestionView {

    fun showQuestion(question: QuestionViewModel)

    fun showButtonFinish()

    fun notifyButtonNextClick(answers: QuestionViewModel)

    fun getSelectedAnswers(): QuestionViewModel

}

interface QuestionPresenter {

    fun viewReady(isLastQuestion: Boolean, question: QuestionViewModel)

    fun onNextButtonClick()

}