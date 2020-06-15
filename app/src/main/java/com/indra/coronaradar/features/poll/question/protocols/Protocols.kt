package com.indra.coronaradar.features.poll.question.protocols

import com.indra.coronaradar.common.viewmodel.QuestionViewModel

interface QuestionView {

    fun showQuestion(question: QuestionViewModel)

}

interface QuestionPresenter {

    fun viewReady(question: QuestionViewModel)

}