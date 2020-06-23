package es.gob.radarcovid.models.request

class RequestPostAnswers : ArrayList<RequestPostAnswer>()

data class RequestPostAnswer(
    val question: Int = -1,
    val option: Int = -1,
    val answer: String = ""
)