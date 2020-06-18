package es.gob.covidradar.features.poll.main.presenter

import android.util.Log
import es.gob.covidradar.common.extensions.toJson
import es.gob.covidradar.common.viewmodel.AnswerViewModel
import es.gob.covidradar.common.viewmodel.QuestionViewModel
import es.gob.covidradar.common.viewmodel.QuestionViewModelFactory
import es.gob.covidradar.datamanager.usecase.GetQuestionsUseCase
import es.gob.covidradar.features.poll.main.protocols.PollPresenter
import es.gob.covidradar.features.poll.main.protocols.PollRouter
import es.gob.covidradar.features.poll.main.protocols.PollView
import javax.inject.Inject

class PollPresenterImpl @Inject constructor(
    private val view: PollView,
    private val router: PollRouter,
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val questionViewModelFactory: QuestionViewModelFactory
) : PollPresenter {

    private lateinit var questions: List<QuestionViewModel>
    private var currentQuestionIndex: Int = 0

    override fun viewReady() {

        requestQuestions()

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

    private fun requestQuestions() {
        view.hideContent()
        view.showLoading()
        getQuestionsUseCase.getQuestions(
            onSuccess = {
                questions = questionViewModelFactory.createQuestionsListViewModel(it)
                view.showQuestion(
                    currentQuestionIndex == questions.size - 1,
                    questions[currentQuestionIndex]
                )
                view.showPollProgress(currentQuestionIndex + 1, questions.size)
                view.showContent()
                view.hideLoading()
            },
            onError = {
                view.showError(it, true)
                view.hideLoading()
            })
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
        1,
        "¿Cómo valoras la información recibida al introducir el código?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel(1, "1", false))
            add(AnswerViewModel(1, "2", false))
            add(AnswerViewModel(1, "3", false))
            add(AnswerViewModel(1, "4", false))
            add(AnswerViewModel(1, "5", false))
        })

    private fun getMockMultipleChoiceSingleSelection() = QuestionViewModel.MultipleChoiceQuestion(
        1,
        "¿Seguiste las recomendaciones sanitarias y de prevención indicadas en la aplicación?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel(1, "Sí, todas", false))
            add(AnswerViewModel(1, "Sí, algunas", false))
            add(AnswerViewModel(1, "No, las seguí", false))

        })

    private fun getMockMultipleChoiceMultipleSelection() = QuestionViewModel.MultipleChoiceQuestion(
        1,
        "¿Seguiste las recomendaciones sanitarias y de prevención indicadas en la aplicación?",
        ArrayList<AnswerViewModel>().apply {
            add(AnswerViewModel(1, "Sí, todas", false))
            add(AnswerViewModel(1, "Sí, algunas", false))
            add(AnswerViewModel(1, "No, las seguí", false))

        }, true
    )

}