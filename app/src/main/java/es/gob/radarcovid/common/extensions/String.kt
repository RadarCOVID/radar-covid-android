package es.gob.radarcovid.common.extensions

import android.text.Spanned
import androidx.core.text.HtmlCompat

fun String?.default(default: String): String = when {
    this == null -> default
    isEmpty() -> default
    else -> this
}

fun String?.parseHtml(): Spanned = HtmlCompat.fromHtml(this ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
