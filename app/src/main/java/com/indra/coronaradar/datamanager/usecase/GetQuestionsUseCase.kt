package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.common.base.asyncRequest
import com.indra.coronaradar.common.base.mapperScope
import com.indra.coronaradar.datamanager.mapper.QuestionsDataMapper
import com.indra.coronaradar.datamanager.repository.ApiRepository
import com.indra.coronaradar.models.domain.Question
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