package es.gob.covidradar.models.exception

class MapperException(
    private val title: String,
    private val detail: String = "The types in the class fields do not match"
) : Exception() {
    override val message: String?
        get() = title

    override val cause: Throwable?
        get() = Throwable(detail)
}