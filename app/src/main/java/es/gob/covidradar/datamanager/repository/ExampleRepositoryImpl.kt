package es.gob.covidradar.datamanager.repository

import es.gob.covidradar.common.base.BaseRepository
import es.gob.covidradar.datamanager.api.ApiInterface
import org.funktionale.either.Either
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ExampleRepository {

    override fun exampleRequest(): Either<Throwable, String> = callService {
        apiInterface.test()
    }

}