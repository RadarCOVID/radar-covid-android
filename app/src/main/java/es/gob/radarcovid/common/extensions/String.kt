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

import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.util.*

fun String?.default(default: String): String = when {
    this == null -> default
    isEmpty() -> default
    else -> this
}

fun String?.parseHtml(): Spanned = HtmlCompat.fromHtml(this ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)

fun String.capitalizeWord(): String {
    val words: List<String> = split(" ")
    var capitalizeWord = ""
    words.forEach {
        val first = it.substring(0, 1)
        val afterFirst = it.substring(1)
        capitalizeWord += first.toUpperCase(Locale.ROOT) + afterFirst + " "
    }
    return capitalizeWord.trim { it <= ' ' }
}
