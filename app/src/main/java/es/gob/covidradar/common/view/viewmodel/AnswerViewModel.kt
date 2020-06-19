package es.gob.covidradar.common.view.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerViewModel(val id: Int, var text: String, var isSelected: Boolean = false) :
    Parcelable