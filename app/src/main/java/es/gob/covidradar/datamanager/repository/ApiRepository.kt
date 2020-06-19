package es.gob.covidradar.datamanager.repository

import es.gob.covidradar.models.request.RequestPostAnswers
import es.gob.covidradar.models.response.ResponseQuestions
import es.gob.covidradar.models.response.ResponseSettings
import es.gob.covidradar.models.response.ResponseUuid
import org.funktionale.either.Either

interface ApiRepository {

    fun getUuid(): Either<Throwable, ResponseUuid>

    fun getSettings(): Either<Throwable, ResponseSettings>

    fun getQuestions(): Either<Throwable, ResponseQuestions>

    fun postAnswers(uuid: String, requestPostAnswers: RequestPostAnswers): Either<Throwable, Unit>

}