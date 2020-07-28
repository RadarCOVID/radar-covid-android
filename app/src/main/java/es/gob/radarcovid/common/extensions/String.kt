package es.gob.radarcovid.common.extensions

fun String?.default(default: String): String = when {
    this == null -> default
    isEmpty() -> default
    else -> this
}
