package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.common.base.asyncRequest
import es.gob.covidradar.common.base.mapperScope
import es.gob.covidradar.datamanager.mapper.QuestionsDataMapper
import es.gob.covidradar.datamanager.repository.ApiRepository
import es.gob.covidradar.models.domain.Question
import org.funktionale.either.Either
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val questionsDataMapper: QuestionsDataMapper
) {

    fun getQuestions(onSuccess: (List<Question>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            val result = apiRepository.getQuestions()
            if (result.isRight())
                mapperScope {
                    questionsDataMapper.transform(result.right().get())
                }
            else
                Either.left(result.left().get())
        }
    }

}