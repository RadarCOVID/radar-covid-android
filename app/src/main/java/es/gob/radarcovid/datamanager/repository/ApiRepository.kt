package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.request.RequestKpiReport
import es.gob.radarcovid.models.request.RequestPostAnswers
import es.gob.radarcovid.models.response.ResponseQuestions
import es.gob.radarcovid.models.response.ResponseSettings
import es.gob.radarcovid.models.response.ResponseUuid
import org.funktionale.either.Either

interface ApiRepository {

    fun getUuid(): Either<Throwable, ResponseUuid>

    fun getSettings(): Either<Throwable, ResponseSettings>

    fun getQuestions(): Either<Throwable, ResponseQuestions>

    fun postAnswers(uuid: String, requestPostAnswers: RequestPostAnswers): Either<Throwable, Unit>

    fun postKpiReport(requestKpiReport: RequestKpiReport): Either<Throwable, Unit>

}