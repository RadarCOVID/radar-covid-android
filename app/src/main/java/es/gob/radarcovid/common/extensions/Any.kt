package es.gob.radarcovid.common.extensions

import com.google.gson.Gson

fun Any?.toJson(): String =
    if (this == null)
        ""
    else
        Gson().toJson(this)