package es.gob.radarcovid.models.response

class ResponseRegions : ArrayList<ResponseRegionsItem>()

data class ResponseRegionsItem(
    val id: String?,
    val description: String?,
    val phone: String?,
    val email: String?
)