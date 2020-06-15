package com.indra.coronaradar.common.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


sealed class QuestionViewModel : Parcelable {

    @Parcelize
    class Rate(
        val id: String = "",
        val text: String = "",
        val answers: List<AnswerViewModel> = emptyList()
    ) : QuestionViewModel()

    @Parcelize
    class MultipleChoice(
        val id: String = "",
        val text: String = "",
        val answers: List<AnswerViewModel> = emptyList(),
        val allowMultipleSelection: Boolean = false
    ) : QuestionViewModel()

}