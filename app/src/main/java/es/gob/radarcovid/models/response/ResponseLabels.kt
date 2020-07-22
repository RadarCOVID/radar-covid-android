package es.gob.radarcovid.models.response

data class ResponseLabels(val includes: ResponseLabelsIncludes?)

data class ResponseLabelsIncludes(val Entry: List<ResponseLabelsEntry>?)

data class ResponseLabelsEntry(val fields: Map<String, String>?)