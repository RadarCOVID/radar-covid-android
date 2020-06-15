package com.indra.coronaradar.features.poll.main.protocols

import com.indra.coronaradar.common.viewmodel.QuestionViewModel

interface PollPresenter {

    fun viewReady()

}

interface PollView {

    fun showQuestion(question: QuestionViewModel)

}