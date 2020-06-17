package com.indra.coronaradar.common.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


sealed class QuestionViewModel : Parcelable, Cloneable {

    @Parcelize
    class RateQuestion(
        var id: Int = -1,
        var text: String = "",
        var answers: List<AnswerViewModel> = emptyList()
    ) : QuestionViewModel()

    @Parcelize
    class MultipleChoiceQuestion(
        var id: Int = -1,
        var text: String = "",
        var answers: List<AnswerViewModel> = emptyList(),
        var allowMultipleSelection: Boolean = false
    ) : QuestionViewModel()

    fun isAnswered(): Boolean = when (this) {
        is RateQuestion -> answers.any { it.isSelected }
        is MultipleChoiceQuestion -> answers.any { it.isSelected }
    }

}