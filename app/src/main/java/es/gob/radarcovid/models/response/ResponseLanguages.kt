package es.gob.radarcovid.models.response

data class ResponseLanguages(val items: List<ResponseLanguagesItem>?)

data class ResponseLanguagesItem(val fields: ResponseLanguagesField?)

data class ResponseLanguagesField(val id: String?, val description: String?)