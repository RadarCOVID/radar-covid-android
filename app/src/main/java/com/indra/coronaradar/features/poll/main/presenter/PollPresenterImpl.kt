package com.indra.coronaradar.features.poll.main.presenter

import com.indra.coronaradar.common.viewmodel.AnswerViewModel
import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import com.indra.coronaradar.features.poll.main.protocols.PollPresenter
import com.indra.coronaradar.features.poll.main.protocols.PollView
import javax.inject.Inject

class PollPresenterImpl @Inject constructor(private val view: PollView) : PollPresenter {

    override fun viewReady() {

        view.showQuestion(getMockRateQuestion())

    }

    private fun getMockRateQuestion() = QuestionViewModel.Rate(
        "id",
        "¿Cómo valoras la información recibida al introducir el código?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel("id", "1", false))
            add(AnswerViewModel("id", "2", false))
            add(AnswerViewModel("id", "3", false))
            add(AnswerViewModel("id", "4", false))
            add(AnswerViewModel("id", "5", false))
        })

}