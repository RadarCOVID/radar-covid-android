package es.gob.covidradar.common.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class QuestionViewModel(
    var id: Int = -1,
    val type: Type = Type.RATE,
    var text: String = "",
    var answers: List<AnswerViewModel> = emptyList(),
    var parentQuestionId: Int = -1,
    var parentAnswerId: Int = -1,
    var minValue: Int = -1,
    var maxValue: Int = -1
) : Parcelable {
    enum class Type { RATE, SINGLE_SELECTION, MULTIPLE_SELECTION, FIELD }

    fun isAnswered(): Boolean = answers.any { it.isSelected }

    fun isAnswerSelected(id: Int) = answers.any { it.isSelected && it.id == id }

    fun isParentQuestion(): Boolean = parentAnswerId == -1

    fun getAnswerById(answerId: Int): AnswerViewModel? = answers.find { it.id == answerId }

    override fun equals(other: Any?): Boolean = (other as? QuestionViewModel)?.id == id

}