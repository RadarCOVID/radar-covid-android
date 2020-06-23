package es.gob.radarcovid.models.domain

data class Question(
    val id: Int = -1,
    val type: Type = Type.RATE,
    val question: String = "",
    val answers: List<Answer> = emptyList(),
    val minValue: Int = 0,
    val maxValue: Int = 0,
    val isMandatory: Boolean = false,
    val parentQuestionId: Int? = -1,
    val parentAnswerId: Int? = -1
) {
    enum class Type { RATE, SINGLE_SELECTION, MULTIPLE_SELECTION, FIELD }
}