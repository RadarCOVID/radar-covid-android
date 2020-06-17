package com.indra.coronaradar.features.poll.main.protocols

import com.indra.coronaradar.common.view.RequestView
import com.indra.coronaradar.common.viewmodel.QuestionViewModel

interface PollView : RequestView {

    fun showContent()

    fun hideContent()

    fun showPollProgress(currentQuestion: Int, totalQuestions: Int)

    fun showQuestion(isLastQuestion: Boolean, question: QuestionViewModel)

    fun showSkipQuestionDialog()

    fun finish()

}

interface PollPresenter {

    fun viewReady()

    fun onBackButtonPressed()

    fun onNextButtonClick(answers: QuestionViewModel)

    fun onContinueWithoutAnswer()

}

interface PollRouter {

    fun navigateToPollCompleted()

}