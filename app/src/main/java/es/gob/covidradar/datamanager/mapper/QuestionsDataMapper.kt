package es.gob.covidradar.datamanager.mapper

import es.gob.covidradar.models.domain.Answer
import es.gob.covidradar.models.domain.Question
import es.gob.covidradar.models.response.*
import javax.inject.Inject

class QuestionsDataMapper @Inject constructor() {

    fun transform(responseQuestions: ResponseQuestions): List<Question> =
        responseQuestions.sortedBy { it.order }.filter {
            when (it.questionType) {
                QUESTION_TYPE_NUMBER, QUESTION_TYPE_TEXT -> false
                else -> true
            }
        }.map { transform(it) }

    fun transform(responseQuestion: ResponseQuestion): Question = with(responseQuestion) {
        Question(
            id = id ?: -1,
            type = when (questionType) {
                QUESTION_TYPE_RATE -> Question.Type.RATE
                QUESTION_TYPE_MULTIPLE_SELECTION -> Question.Type.MULTIPLE_SELECTION
                QUESTION_TYPE_TEXT -> Question.Type.FIELD
                else -> Question.Type.SINGLE_SELECTION
            },
            question = question ?: "",
            answers =
            if (questionType == QUESTION_TYPE_TEXT)
                arrayListOf(Answer())
            else
                transform(options),
            minValue = minValue ?: 0,
            maxValue = maxValue ?: 0,
            isMandatory = mandatory ?: false,
            parentQuestionId = parentId ?: -1,
            parentAnswerId = parentOptionId ?: -1
        )
    }

    private fun transform(responseQuestionOptions: List<ResponseQuestionOption>?): List<Answer> =
        responseQuestionOptions?.sortedBy { it.order }?.map { transform(it) } ?: emptyList()

    private fun transform(responseQuestionOption: ResponseQuestionOption): Answer =
        with(responseQuestionOption) {
            Answer(id ?: -1, option ?: "")
        }

}