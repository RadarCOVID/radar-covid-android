package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.common.base.asyncRequest
import es.gob.covidradar.common.base.mapperScope
import es.gob.covidradar.common.view.viewmodel.QuestionViewModel
import es.gob.covidradar.datamanager.mapper.QuestionsDataMapper
import es.gob.covidradar.datamanager.repository.ApiRepository
import es.gob.covidradar.datamanager.repository.PreferencesRepository
import es.gob.covidradar.models.domain.Question
import es.gob.covidradar.models.request.RequestPostAnswer
import es.gob.covidradar.models.request.RequestPostAnswers
import javax.inject.Inject

class PollUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val questionsDataMapper: QuestionsDataMapper
) {

    fun isPollCompleted(): Boolean = preferencesRepository.isPollCompleted()

    fun setPollCompleted(pollCompleted: Boolean) =
        preferencesRepository.setPollCompleted(pollCompleted)

    fun getQuestions(onSuccess: (List<Question>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(apiRepository.getQuestions()) {
                questionsDataMapper.transform(it)
            }
        }
    }

    fun postAnswers(
        answeredQuestions: List<QuestionViewModel>,
        onSuccess: (Unit) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        asyncRequest(onSuccess, onError) {
            apiRepository.postAnswers(
                preferencesRepository.getUuid(),
                createPostAnswersRequest(answeredQuestions)
            )
        }
    }

    private fun createPostAnswersRequest(answeredQuestions: List<QuestionViewModel>): RequestPostAnswers =
        RequestPostAnswers().apply {
            answeredQuestions.forEach { answeredQuestion ->
                addAll(createPostAnswer(answeredQuestion))
            }
        }

    private fun createPostAnswer(answeredQuestion: QuestionViewModel): List<RequestPostAnswer> =
        answeredQuestion.answers.filter { answer -> answer.isSelected }.map { selectedAnswer ->
            RequestPostAnswer(answeredQuestion.id, selectedAnswer.id, selectedAnswer.text)
        }

}