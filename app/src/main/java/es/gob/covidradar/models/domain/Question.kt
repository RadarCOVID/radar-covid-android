package es.gob.covidradar.models.domain

data class Question(
    val id: Int = -1,
    val type: Type = Type.RATE,
    val question: String = "",
    val answers: List<Answer> = emptyList(),
    val minValue: Int = 0,
    val maxValue: Int = 0,
    val isMandatory: Boolean = false
) {
    enum class Type { RATE, SINGLE_SELECTION, MULTIPLE_SELECTION }
}