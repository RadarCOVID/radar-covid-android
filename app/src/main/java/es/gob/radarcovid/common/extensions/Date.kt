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

const val DATE_FORMAT_VERBOSE = "dd.MM.yyyy"
const val DATE_FORMAT_TIMESTAMP = "dd/MM/yyyy HH:mm:ss"
const val DATE_FORMAT = "dd/MM/yyyy"

fun Date.format(): String = SimpleDateFormat(DATE_FORMAT_VERBOSE, Locale.getDefault()).format(this)

fun Date.toTimeStamp(): String =
    SimpleDateFormat(DATE_FORMAT_TIMESTAMP, Locale.getDefault()).format(this)

fun Date.getDayString(): String =
    SimpleDateFormat("dd", Locale.getDefault()).format(this)

fun Date.geMonthName(locale: String): String =
    SimpleDateFormat("MMMM", Locale.forLanguageTag(locale)).format(this).capitalize()

fun Date.getYearString(): String =
    SimpleDateFormat("yyyy", Locale.getDefault()).format(this)

fun Date.getHourString(): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

fun Date.toDateFormat(): String =
    SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(this)
