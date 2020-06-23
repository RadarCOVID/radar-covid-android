package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.common.base.BaseRepository
import es.gob.radarcovid.datamanager.api.ApiInterface
import org.funktionale.either.Either
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ExampleRepository {

    override fun exampleRequest(): Either<Throwable, String> = callService {
        apiInterface.test()
    }

}