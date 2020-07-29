package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.common.base.BaseRepository
import es.gob.radarcovid.datamanager.api.ApiInterface
import es.gob.radarcovid.models.request.RequestKpiReport
import es.gob.radarcovid.models.request.RequestPostAnswers
import es.gob.radarcovid.models.response.*
import org.funktionale.either.Either
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ApiRepository {

    override fun getUuid(): Either<Throwable, ResponseUuid> = callService {
        apiInterface.getUuid()
    }

    override fun getSettings(): Either<Throwable, ResponseSettings> = callService {
        apiInterface.getSettings()
    }

    override fun getLabels(
        uuid: String,
        language: String,
        region: String
    ): Either<Throwable, ResponseLabels> =
        callService {
            apiInterface.getLabels(uuid, language, region)
        }

    override fun getLanguages(
        uuid: String,
        language: String
    ): Either<Throwable, ResponseLanguages> =
        callService {
            apiInterface.getLanguages(uuid, language)
        }

    override fun getRegions(uuid: String, language: String): Either<Throwable, ResponseRegions> =
        callService {
            apiInterface.getRegions(uuid, language, true)
        }

    override fun getQuestions(): Either<Throwable, ResponseQuestions> = callService {
        apiInterface.getQuestions()
    }

    override fun postAnswers(
        uuid: String,
        requestPostAnswers: RequestPostAnswers
    ): Either<Throwable, Unit> = callService {
        apiInterface.postAnswers(uuid, requestPostAnswers)
    }

    override fun postKpiReport(requestKpiReport: RequestKpiReport): Either<Throwable, Unit> =
        callService {
            apiInterface.postKpiReport(requestKpiReport)
        }

}