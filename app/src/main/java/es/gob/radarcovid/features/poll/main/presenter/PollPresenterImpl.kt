package es.gob.radarcovid.features.poll.main.presenter

import es.gob.radarcovid.common.view.viewmodel.QuestionViewModel
import es.gob.radarcovid.common.view.viewmodel.QuestionViewModelFactory
import es.gob.radarcovid.datamanager.usecase.PollUseCase
import es.gob.radarcovid.features.poll.main.protocols.PollPresenter
import es.gob.radarcovid.features.poll.main.protocols.PollRouter
import es.gob.radarcovid.features.poll.main.protocols.PollView
import es.gob.radarcovid.models.domain.Question
import javax.inject.Inject

class PollPresenterImpl @Inject constructor(
    private val view: PollView,
    private val router: PollRouter,
    private val pollUseCase: PollUseCase,
    private val questionViewModelFactory: QuestionViewModelFactory
) : PollPresenter {

    private lateinit var parentQuestions: List<QuestionViewModel>
    private lateinit var childQuestions: List<QuestionViewModel>

    override fun viewReady() {

        requestQuestions()

    }

    override fun onBackButtonPressed() {
        showPreviousQuestion(view.getCurrentQuestion())
    }

    override fun onNextButtonClick(currentQuestion: QuestionViewModel) {
        if (currentQuestion.isAnswered())
            showNextQuestion(currentQuestion)
        else
            view.showSkipQuestionDialog()
    }

    override fun onContinueWithoutAnswer() {
        showNextQuestion(view.getCurrentQuestion())
    }

    private fun showNextQuestion(currentQuestion: QuestionViewModel) {
        val nextQuestion = getNextQuestion(currentQuestion)
        if (nextQuestion == null) {
            requestPostAnswers(parentQuestions + childQuestions)
        } else {
            var parentQuestionIndex = 0
            if (nextQuestion.isParentQuestion()) {
                parentQuestionIndex = parentQuestions.indexOf(nextQuestion)
                view.showPollProgress(
                    parentQuestions.indexOf(nextQuestion) + 1,
                    parentQuestions.size
                )
                view.showQuestion(parentQuestionIndex == parentQuestions.size - 1, nextQuestion)
            } else {
                parentQuestionIndex = parentQuestions.indexOf(getParentQuestion(nextQuestion))
                view.showQuestion(parentQuestionIndex == parentQuestions.size - 1, nextQuestion)
            }
        }
    }

    private fun showPreviousQuestion(currentQuestion: QuestionViewModel) {
        val previousQuestion = getPreviousQuestion(currentQuestion)
        if (previousQuestion == null) {
            view.finish()
        } else {
            if (currentQuestion.isParentQuestion()) {
                view.showPollProgress(
                    parentQuestions.indexOf(currentQuestion),
                    parentQuestions.size
                )
                view.showQuestion(false, previousQuestion)
            } else {
                val parentQuestionIndex =
                    parentQuestions.indexOf(getParentQuestion(previousQuestion))
                view.showQuestion(parentQuestionIndex == parentQuestions.size - 1, previousQuestion)
            }
        }
    }

    private fun getNextQuestion(currentQuestion: QuestionViewModel): QuestionViewModel? =
        if (currentQuestion.isParentQuestion()) {
            getFirstChildQuestion(currentQuestion) ?: getNextParentQuestion(currentQuestion)
        } else {
            val brothers = childQuestions.filter {
                it.parentQuestionId == currentQuestion.parentQuestionId
                        && it.parentAnswerId == currentQuestion.parentAnswerId
            }
            if (brothers.last() == currentQuestion) {
                getNextParentQuestion(getParentQuestion(currentQuestion))
            } else {
                brothers[brothers.indexOf(currentQuestion) + 1]
            }
        }

    private fun getPreviousQuestion(currentQuestion: QuestionViewModel): QuestionViewModel? =
        if (currentQuestion.isParentQuestion()) {
            getPreviousParentQuestion(currentQuestion)?.let { previousParentQuestion ->
                getLastChildQuestion(previousParentQuestion) ?: previousParentQuestion
            }
        } else {
            val brothers = childQuestions.filter {
                it.parentQuestionId == currentQuestion.parentQuestionId
                        && it.parentAnswerId == currentQuestion.parentAnswerId
            }
            if (brothers.first() == currentQuestion) {
                getParentQuestion(currentQuestion)
            } else {
                brothers[brothers.indexOf(currentQuestion) - 1]
            }
        }


    private fun getFirstChildQuestion(parentQuestion: QuestionViewModel): QuestionViewModel? =
        childQuestions.find {
            it.parentQuestionId == parentQuestion.id && parentQuestion.isAnswerSelected(
                it.parentAnswerId
            )
        }

    private fun getLastChildQuestion(parentQuestion: QuestionViewModel?): QuestionViewModel? =
        childQuestions.findLast {
            it.parentQuestionId == parentQuestion?.id && parentQuestion.isAnswerSelected(
                it.parentAnswerId
            )
        }


    private fun getParentQuestion(childQuestion: QuestionViewModel): QuestionViewModel =
        (parentQuestions + childQuestions).find { childQuestion.parentQuestionId == it.id }
            ?: QuestionViewModel()

    private fun getNextParentQuestion(parentQuestion: QuestionViewModel): QuestionViewModel? =
        parentQuestions.indexOf(parentQuestion).let { parentIndex ->
            if (parentIndex == parentQuestions.size - 1)
                null
            else
                parentQuestions[parentIndex + 1]
        }

    private fun getPreviousParentQuestion(parentQuestion: QuestionViewModel): QuestionViewModel? =
        parentQuestions.indexOf(parentQuestion).let { parentIndex ->
            if (parentIndex == 0)
                null
            else
                parentQuestions[parentIndex - 1]
        }

    private fun requestQuestions() {
        view.hideContent()
        view.showLoading()
        pollUseCase.getQuestions(
            onSuccess = {
                onRequestQuestionsSuccess(it)
                view.showContent()
                view.hideLoading()
            },
            onError = {
                onRequestQuestionsError(it)
                view.hideLoading()
            })
    }

    private fun onRequestQuestionsSuccess(questions: List<Question>) {
        val questionViewModelList = questionViewModelFactory.createQuestionsListViewModel(questions)
        parentQuestions = questionViewModelList.filter { it.isParentQuestion() }
        childQuestions = questionViewModelList.filter { !it.isParentQuestion() }
        view.showQuestion(
            parentQuestions.size == 1,
            parentQuestions[0]
        )
        view.showPollProgress(1, parentQuestions.size)
    }

    private fun onRequestQuestionsError(error: Throwable) {
        view.showError(error, true)
    }

    private fun requestPostAnswers(answeredQuestions: List<QuestionViewModel>) {
        view.showLoading()
        pollUseCase.postAnswers(answeredQuestions,
            onSuccess = {
                view.hideLoading()
                onPostAnswersSuccess()
            },
            onError = {
                view.hideLoading()
                onPostAnswersError(it)
            })
    }

    private fun onPostAnswersSuccess() {
        pollUseCase.setPollCompleted(true)
        router.navigateToPollCompleted()
        view.finish()
    }

    private fun onPostAnswersError(throwable: Throwable) {
        view.showError(throwable)
    }
}