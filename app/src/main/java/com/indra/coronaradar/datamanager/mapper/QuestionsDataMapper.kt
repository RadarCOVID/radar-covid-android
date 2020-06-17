package com.indra.coronaradar.datamanager.mapper

import com.indra.coronaradar.models.domain.Answer
import com.indra.coronaradar.models.domain.Question
import com.indra.coronaradar.models.response.ResponseQuestion
import com.indra.coronaradar.models.response.ResponseQuestionOption
import com.indra.coronaradar.models.response.ResponseQuestions
import javax.inject.Inject

class QuestionsDataMapper @Inject constructor() {

    fun transform(responseQuestions: ResponseQuestions): List<Question> =
        responseQuestions.sortedBy { it.order }.map { transform(it) }

    fun transform(responseQuestion: ResponseQuestion): Question = with(responseQuestion) {
        Question(
            id = id ?: -1,
            type = when (questionType) {
                2 -> Question.Type.SINGLE_SELECTION
                3 -> Question.Type.MULTIPLE_SELECTION
                else -> Question.Type.RATE
            },
            question = question ?: "",
            answers = transform(options),
            minValue = minValue ?: 0,
            maxValue = maxValue ?: 0,
            isMandatory = mandatory ?: false
        )
    }

    private fun transform(responseQuestionOptions: List<ResponseQuestionOption>?): List<Answer> =
        responseQuestionOptions?.sortedBy { it.order }?.map { transform(it) } ?: emptyList()

    private fun transform(responseQuestionOption: ResponseQuestionOption): Answer =
        with(responseQuestionOption) {
            Answer(id ?: -1, option ?: "")
        }

}