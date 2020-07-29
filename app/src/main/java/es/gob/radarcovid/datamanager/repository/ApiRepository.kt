package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.request.RequestKpiReport
import es.gob.radarcovid.models.request.RequestPostAnswers
import es.gob.radarcovid.models.response.*
import org.funktionale.either.Either

interface ApiRepository {

    fun getUuid(): Either<Throwable, ResponseUuid>

    fun getSettings(): Either<Throwable, ResponseSettings>

    fun getLabels(uuid: String, language: String, region: String): Either<Throwable, ResponseLabels>

    fun getLanguages(uuid: String, language: String): Either<Throwable, ResponseLanguages>

    fun getRegions(uuid: String, language: String): Either<Throwable, ResponseRegions>

    fun getQuestions(): Either<Throwable, ResponseQuestions>

    fun postAnswers(uuid: String, requestPostAnswers: RequestPostAnswers): Either<Throwable, Unit>

    fun postKpiReport(requestKpiReport: RequestKpiReport): Either<Throwable, Unit>

}