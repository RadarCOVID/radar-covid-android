package com.indra.coronaradar.features.poll.main.presenter

import android.util.Log
import com.indra.coronaradar.common.extensions.toJson
import com.indra.coronaradar.common.viewmodel.AnswerViewModel
import com.indra.coronaradar.common.viewmodel.QuestionViewModel
import com.indra.coronaradar.features.poll.main.protocols.PollPresenter
import com.indra.coronaradar.features.poll.main.protocols.PollRouter
import com.indra.coronaradar.features.poll.main.protocols.PollView
import javax.inject.Inject

class PollPresenterImpl @Inject constructor(
    private val view: PollView,
    private val router: PollRouter
) : PollPresenter {

    private lateinit var questions: List<QuestionViewModel>
    private var currentQuestionIndex: Int = 0

    override fun viewReady() {

        questions = getMockQuestionsList()

        view.showQuestion(
            currentQuestionIndex == questions.size - 1,
            questions[currentQuestionIndex]
        )
        view.showPollProgress(currentQuestionIndex + 1, questions.size)

    }

    override fun onBackButtonPressed() {
        currentQuestionIndex--
        if (currentQuestionIndex < 0)
            view.finish()
        else
            view.showPollProgress(currentQuestionIndex + 1, questions.size)
    }

    override fun onNextButtonClick(answers: QuestionViewModel) {
        //TODO GET CURRENT QUESTION ANSWERS INFORMATION
        Log.d("test", answers.toJson())

        if (answers.isAnswered())
            showNextQuestion()
        else
            view.showSkipQuestionDialog()
    }

    override fun onContinueWithoutAnswer() {
        showNextQuestion()
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex + 1 < questions.size) {
            currentQuestionIndex++
            view.showQuestion(
                currentQuestionIndex == questions.size - 1,
                questions[currentQuestionIndex]
            )
            view.showPollProgress(currentQuestionIndex + 1, questions.size)
        } else {
            //TODO MAKE REQUEST
            router.navigateToPollCompleted()
            view.finish()
        }
    }

    private fun getMockQuestionsList() =
        arrayListOf(
            getMockRateQuestion(),
            getMockMultipleChoiceSingleSelection(),
            getMockMultipleChoiceMultipleSelection()
        )

    private fun getMockRateQuestion() = QuestionViewModel.RateQuestion(
        "id",
        "¿Cómo valoras la información recibida al introducir el código?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel("id", "1", false))
            add(AnswerViewModel("id", "2", false))
            add(AnswerViewModel("id", "3", false))
            add(AnswerViewModel("id", "4", false))
            add(AnswerViewModel("id", "5", false))
        })

    private fun getMockMultipleChoiceSingleSelection() = QuestionViewModel.MultipleChoiceQuestion(
        "id",
        "¿Seguiste las recomendaciones sanitarias y de prevención indicadas en la aplicación?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel("id", "Sí, todas", false))
            add(AnswerViewModel("id", "Sí, algunas", false))
            add(AnswerViewModel("id", "No, las seguí", false))

        })

    private fun getMockMultipleChoiceMultipleSelection() = QuestionViewModel.MultipleChoiceQuestion(
        "id",
        "¿Seguiste las recomendaciones sanitarias y de prevención indicadas en la aplicación?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel("id", "Sí, todas", false))
            add(AnswerViewModel("id", "Sí, algunas", false))
            add(AnswerViewModel("id", "No, las seguí", false))

        }, true
    )

}