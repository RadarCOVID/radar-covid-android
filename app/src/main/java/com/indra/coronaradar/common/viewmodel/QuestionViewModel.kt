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
    class SingleSelection(
        val id: String = "",
        val text: String = "",
        val answers: List<AnswerViewModel> = emptyList()
    ) : QuestionViewModel()

    @Parcelize
    class MultipleSelection(
        val id: String = "",
        val text: String = "",
        val answers: List<AnswerViewModel> = emptyList()
    ) : QuestionViewModel()

}