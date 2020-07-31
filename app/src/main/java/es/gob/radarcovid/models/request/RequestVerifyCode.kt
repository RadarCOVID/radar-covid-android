package es.gob.radarcovid.models.request

import java.util.Date

data class RequestVerifyCode(
 val date: Date?,
 val code: String
)