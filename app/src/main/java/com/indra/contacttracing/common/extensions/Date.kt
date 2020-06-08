package com.indra.contacttracing.common.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_VERBOSE = "dd.MM.yyyy"

fun Date.format(): String = SimpleDateFormat(DATE_FORMAT_VERBOSE, Locale.getDefault()).format(this)