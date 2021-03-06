/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val DATE_FORMAT_VERBOSE = "dd.MM.yyyy"
const val DATE_FORMAT_TIMESTAMP = "dd/MM/yyyy HH:mm:ss"
const val DATE_FORMAT = "dd/MM/yyyy"
const val DATE_DAY_FORMAT = "MMMM d"

fun Date.format(): String = SimpleDateFormat(DATE_FORMAT_VERBOSE, Locale.getDefault()).format(this)

fun Date.toTimeStamp(): String =
    SimpleDateFormat(DATE_FORMAT_TIMESTAMP, Locale.getDefault()).format(this)

fun Date.getDayString(): String =
    SimpleDateFormat("dd", Locale.getDefault()).format(this)

fun Date.getNameDayString(locale: String): String =
    SimpleDateFormat("EEEE", Locale.forLanguageTag(locale)).format(this).capitalize()

fun Date.geMonthNameDefault(): String =
    SimpleDateFormat("MMMM", Locale.getDefault()).format(this).capitalize()

fun Date.geMonthName(locale: String): String =
    SimpleDateFormat("MMMM", Locale.forLanguageTag(locale)).format(this).capitalize()

fun Date.getYearString(): String =
    SimpleDateFormat("yyyy", Locale.getDefault()).format(this)

fun Date.getHourString(): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

fun Date.getDayAndMonth(locale: String): String =
    SimpleDateFormat("d MMMM", Locale.forLanguageTag(locale)).format(this).capitalizeWord()

fun Date.toDateFormat(): String =
    SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(this)

fun Date.isToday(): Boolean {
    Calendar.getInstance().apply {
        return this.get(Calendar.DAY_OF_YEAR) == get(Calendar.DAY_OF_YEAR)
    }
}

fun Date.isYesterday(): Boolean {
    Calendar.getInstance().apply {
        return this.get(Calendar.DAY_OF_YEAR) - get(Calendar.DAY_OF_YEAR) == 1
    }
}

fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}

fun Date.getDaysAgo(): Long {
    val millisElapsed = System.currentTimeMillis() - time
    return TimeUnit.MILLISECONDS.toDays(millisElapsed)
}

fun Date.getTimeElapsed(date: Date): String {
    val millisElapsed = date.time - time
    val hours = TimeUnit.MILLISECONDS.toHours(millisElapsed)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisElapsed)
    return if (hours <= 0) {
        "$minutes'"
    } else {
        "${hours}h"
    }
}