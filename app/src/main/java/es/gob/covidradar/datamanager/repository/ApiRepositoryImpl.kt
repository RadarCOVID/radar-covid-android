package es.gob.covidradar.datamanager.repository

import es.gob.covidradar.common.base.BaseRepository
import es.gob.covidradar.datamanager.api.ApiInterface
import es.gob.covidradar.models.response.ResponseQuestions
import es.gob.covidradar.models.response.ResponseSettings
import es.gob.covidradar.models.response.ResponseUuid
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

}