package com.indra.coronaradar.features.poll.main.protocols

import com.indra.coronaradar.common.viewmodel.QuestionViewModel

interface PollPresenter {

    fun viewReady()

    fun onBackButtonPressed()

    fun onNextButtonClick(answers: QuestionViewModel)

    fun onContinueWithoutAnswer()

}

interface PollView {

    fun showPollProgress(currentQuestion: Int, totalQuestions: Int)

    fun showQuestion(isLastQuestion: Boolean, question: QuestionViewModel)

    fun showSkipQuestionDialog()

    fun finish()

}