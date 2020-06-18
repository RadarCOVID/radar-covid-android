package es.gob.covidradar.features.poll.main.presenter

import es.gob.covidradar.common.viewmodel.QuestionViewModel
import es.gob.covidradar.common.viewmodel.QuestionViewModelFactory
import es.gob.covidradar.datamanager.usecase.GetQuestionsUseCase
import es.gob.covidradar.features.poll.main.protocols.PollPresenter
import es.gob.covidradar.features.poll.main.protocols.PollRouter
import es.gob.covidradar.features.poll.main.protocols.PollView
import es.gob.covidradar.models.domain.Question
import javax.inject.Inject

class PollPresenterImpl @Inject constructor(
    private val view: PollView,
    private val router: PollRouter,
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val questionViewModelFactory: QuestionViewModelFactory
) : PollPresenter {

    private lateinit var parentQuestions: List<QuestionViewModel>
    private lateinit var childQuestions: List<QuestionViewModel>
    private var currentQuestionIndex: Int = 0

    override fun viewReady() {

        requestQuestions()

    }

    override fun onBackButtonPressed() {
        currentQuestionIndex--
        if (currentQuestionIndex < 0)
            view.finish()
        else
            view.showPollProgress(currentQuestionIndex + 1, parentQuestions.size)
    }

    override fun onNextButtonClick(currentQuestion: QuestionViewModel) {
        if (currentQuestion.isAnswered())
            showNextQuestion(currentQuestion)
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
                onRequestQuestionsSuccess(it)
                view.showContent()
                view.hideLoading()
            },
            onError = {
                onRequestQuestionsError(it)
                view.hideLoading()
                view.showContent()
            })
    }

    private fun onRequestQuestionsSuccess(questions: List<Question>) {
        val questionViewModelList = questionViewModelFactory.createQuestionsListViewModel(questions)
        parentQuestions = questionViewModelList.filter { it.isParentQuestion() }
        childQuestions = questionViewModelList.filter { !it.isParentQuestion() }
        view.showQuestion(
            currentQuestionIndex == parentQuestions.size - 1,
            parentQuestions[currentQuestionIndex]
        )
        view.showPollProgress(currentQuestionIndex + 1, parentQuestions.size)
    }

    private fun onRequestQuestionsError(error: Throwable) {
        view.showError(error, true)
    }

    private fun showNextQuestion(currentQuestion: QuestionViewModel? = null) {
        if (currentQuestion != null) {
            val childQuestion = getChildQuestion(currentQuestion)
            if (childQuestion != null)
                view.showQuestion(
                    false,
                    childQuestion
                )
            else
                showNextParentQuestion()
        } else {
            showNextParentQuestion()
        }
    }

    private fun showNextParentQuestion() {
        if (currentQuestionIndex + 1 < parentQuestions.size) {
            currentQuestionIndex++
            view.showQuestion(
                currentQuestionIndex == parentQuestions.size - 1,
                parentQuestions[currentQuestionIndex]
            )
            view.showPollProgress(currentQuestionIndex + 1, parentQuestions.size)
        } else {
            //TODO MAKE REQUEST
            router.navigateToPollCompleted()
            view.finish()
        }
    }

    private fun getChildQuestion(parentQuestion: QuestionViewModel): QuestionViewModel? {
        var childQuestion: QuestionViewModel? = null
        childQuestions.filter { it.parentQuestionId == parentQuestion.id }
            .forEach { selectedChildQuestion ->
                parentQuestion.answers.filter { it.isSelected }.forEach { selectedAnswer ->
                    if (selectedAnswer.id == selectedChildQuestion.parentAnswerId)
                        childQuestion = selectedChildQuestion
                }
            }
        return childQuestion
    }

//    private fun getMockQuestionsList() =
//        arrayListOf(
//            getMockRateQuestion(),
//            getMockMultipleChoiceSingleSelection(),
//            getMockMultipleChoiceMultipleSelection()
//        )
//
//    private fun getMockRateQuestion() = QuestionViewModel.RateQuestion(
//        1,
//        "¿Cómo valoras la información recibida al introducir el código?",
//        ArrayList<AnswerViewModel>().apply {
//            add(AnswerViewModel(1, "1", false))
//            add(AnswerViewModel(1, "2", false))
//            add(AnswerViewModel(1, "3", false))
//            add(AnswerViewModel(1, "4", false))
//            add(AnswerViewModel(1, "5", false))
//        })
//
//    private fun getMockMultipleChoiceSingleSelection() = QuestionViewModel.MultipleChoiceQuestion(
//        1,
//        "¿Seguiste las recomendaciones sanitarias y de prevención indicadas en la aplicación?",
//        ArrayList<AnswerViewModel>().apply {
//            add(AnswerViewModel(1, "Sí, todas", false))
//            add(AnswerViewModel(1, "Sí, algunas", false))
//            add(AnswerViewModel(1, "No, las seguí", false))
//
//        })
//
//    private fun getMockMultipleChoiceMultipleSelection() = QuestionViewModel.MultipleChoiceQuestion(
//        1,
//        "¿Seguiste las recomendaciones sanitarias y de prevención indicadas en la aplicación?",
//        ArrayList<AnswerViewModel>().apply {
//            add(AnswerViewModel(1, "Sí, todas", false))
//            add(AnswerViewModel(1, "Sí, algunas", false))
//            add(AnswerViewModel(1, "No, las seguí", false))
//
//        }, true
//    )

}