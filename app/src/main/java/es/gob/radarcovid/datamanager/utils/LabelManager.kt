/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.utils

import android.content.Context
import android.text.Spanned
import android.text.SpannedString
import androidx.core.text.HtmlCompat
import es.gob.radarcovid.R
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import javax.inject.Inject
import javax.inject.Named

@PerApplication
class LabelManager @Inject constructor(
    @Named("applicationContext") private val context: Context,
    private val repository: PreferencesRepository
) {

    private var labels: Map<String, String> = repository.getLabels()

    fun reload() {
        labels = repository.getLabels()
    }

    fun getText(labelId: String?, defaultResId: Int): Spanned =
        getText(labelId, context.getText(defaultResId))

    fun getText(labelId: String?, default: CharSequence?): Spanned = labels[labelId]?.let {
        HtmlCompat.fromHtml(
            it,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    } ?: SpannedString(default ?: "")


    fun getExposureHighDatesText(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    ): Spanned {
        var text: String
        if (daysElapsed != null && hoursElapsed != null && minutesElapsed != null) {
            text = getFormattedText("EXPOSITION_HIGH_DESCRIPTION", daysElapsed.toString(), date)
            if (text.isEmpty())
                text = when {
                    daysElapsed > 0 -> {
                        val daysText =
                            context.resources.getQuantityString(
                                R.plurals.days,
                                daysElapsed,
                                daysElapsed
                            )
                        context.getString(R.string.exposure_detail_high_last_update, daysText, date)
                    }
                    hoursElapsed > 0 -> {
                        val hoursText =
                            context.resources.getQuantityString(
                                R.plurals.hours,
                                hoursElapsed,
                                hoursElapsed
                            )
                        context.getString(
                            R.string.exposure_detail_high_last_update,
                            hoursText,
                            date
                        )
                    }
                    else -> {
                        val minutesText =
                            context.resources.getQuantityString(
                                R.plurals.minutes,
                                minutesElapsed,
                                minutesElapsed
                            )
                        context.getString(
                            R.string.exposure_detail_high_last_update,
                            minutesText,
                            date
                        )
                    }
                }
        } else {
            text = getFormattedText("EXPOSITION_LOW_DESCRIPTION", date)
                .default(context.getString(R.string.exposure_detail_low_last_update, date))
        }
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun getFormattedText(labelId: String, vararg values: String): String {
        var text: String = labels[labelId] ?: ""
        return if (text.isNotEmpty()) {
            values.forEach {
                text = text.replaceFirst("%@", it)
            }
            text
        } else {
            text
        }
    }

    fun getContactPhone(): String =
        getText("CONTACT_PHONE", R.string.contact_support_phone).toString()

}