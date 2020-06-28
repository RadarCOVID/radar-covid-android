package es.gob.radarcovid.common.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_VERBOSE = "dd.MM.yyyy"
const val DATE_FORMAT_TIMESTAMP = "dd/MM/yyyy HH:mm:ss.SSS z"

fun Date.format(): String = SimpleDateFormat(DATE_FORMAT_VERBOSE, Locale.getDefault()).format(this)

fun Date.toTimeStamp(): String =
    SimpleDateFormat(DATE_FORMAT_TIMESTAMP, Locale.getDefault()).format(this)