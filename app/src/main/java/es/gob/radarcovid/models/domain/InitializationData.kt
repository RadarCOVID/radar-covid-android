package es.gob.radarcovid.models.domain

data class InitializationData(
    val uuid: String,
    val settings: Settings,
    val labels: Map<String, String>,
    val languages: List<Language>,
    val regions: List<Region>
)