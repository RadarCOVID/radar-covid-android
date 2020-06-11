package com.indra.coronaradar.common.extensions

import com.google.gson.Gson

fun Any.toJson() = Gson().toJson(this)