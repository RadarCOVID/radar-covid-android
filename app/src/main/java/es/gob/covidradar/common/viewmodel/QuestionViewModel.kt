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
    var parentAnswerId: Int = -1
) : Parcelable {
    enum class Type { RATE, SINGLE_SELECTION, MULTIPLE_SELECTION }

    fun isAnswered(): Boolean = answers.any { it.isSelected }

    fun isParentQuestion(): Boolean = parentAnswerId == -1

    fun getAnswerById(answerId: Int): AnswerViewModel? = answers.find { it.id == answerId }

}