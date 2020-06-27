package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.common.base.BaseRepository
import es.gob.radarcovid.datamanager.api.ApiInterface
import es.gob.radarcovid.models.request.RequestKpiReport
import es.gob.radarcovid.models.request.RequestPostAnswers
import es.gob.radarcovid.models.response.ResponseQuestions
import es.gob.radarcovid.models.response.ResponseSettings
import es.gob.radarcovid.models.response.ResponseUuid
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