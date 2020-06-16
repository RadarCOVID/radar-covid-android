package com.indra.coronaradar.common.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerViewModel(val id: String, val text: String, var isSelected: Boolean = false) :
    Parcelable