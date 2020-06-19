package es.gob.covidradar.models.response

const val QUESTION_TYPE_RATE = "RATING_SCALE"
const val QUESTION_TYPE_SINGLE_SELECTION = "CHECKBOX"
const val QUESTION_TYPE_MULTIPLE_SELECTION = "MULTIPLE_CHOICE"
const val QUESTION_TYPE_NUMBER = "OPEN_ENDED_NUMBER"
const val QUESTION_TYPE_TEXT = "OPEN_ENDED_TEXT"
const val QUESTION_TYPE_DICHOTOMOUS = "DICHOTOMOUS"

class ResponseQuestions : ArrayList<ResponseQuestion>()

data class ResponseQuestion(
    val id: Int? = null,
    val order: Int? = null,
    val question: String? = null,
    val questionType: String? = null,
    val options: List<ResponseQuestionOption>? = null,
    val minValue: Int? = null,
    val maxValue: Int? = null,
    val mandatory: Boolean? = null,
    val parentId: Int? = -1,
    val parentOptionId: Int? = -1
)

data class ResponseQuestionOption(
    val id: Int? = null,
    val order: Int? = null,
    val option: String? = null,
    val other: Boolean? = false
)