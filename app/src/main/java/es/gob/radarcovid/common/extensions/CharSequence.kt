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

import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.URLSpan
import android.text.style.UnderlineSpan

fun CharSequence.removeUnderline(): Spanned {
    val s = this as Spannable
    for (u in s.getSpans(0, s.length, URLSpan::class.java)) {
        s.setSpan(object : UnderlineSpan() {
            override fun updateDrawState(tp: TextPaint) {
                tp.isUnderlineText = false
            }
        }, s.getSpanStart(u), s.getSpanEnd(u), 0)
    }
    return s
}