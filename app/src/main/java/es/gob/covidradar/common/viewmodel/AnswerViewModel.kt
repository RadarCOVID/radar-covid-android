package es.gob.covidradar.common.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerViewModel(val id: Int, val text: String, var isSelected: Boolean = false) :
    Parcelable