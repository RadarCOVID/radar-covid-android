package com.indra.coronaradar.models.response

class ResponseQuestions : ArrayList<ResponseQuestion>()

data class ResponseQuestion(
    val id: Int? = null,
    val order: Int? = null,
    val question: String? = null,
    val questionType: Int? = null,
    val options: List<ResponseQuestionOption>? = null,
    val minValue: Int? = null,
    val maxValue: Int? = null,
    val mandatory: Boolean? = null
)

data class ResponseQuestionOption(
    val id: Int? = null,
    val order: Int? = null,
    val option: String? = null
)