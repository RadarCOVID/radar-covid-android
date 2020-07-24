package es.gob.radarcovid.models.response

data class ResponseRegions(val items: List<ResponseRegionsItem>?)

data class ResponseRegionsItem(val fields: ResponseRegionsField?)

data class ResponseRegionsField(val id: String?, val description: String?)