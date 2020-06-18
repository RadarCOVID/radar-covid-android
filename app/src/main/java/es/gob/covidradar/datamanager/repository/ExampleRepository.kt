package es.gob.covidradar.datamanager.repository

import org.funktionale.either.Either

interface ExampleRepository {

    fun exampleRequest(): Either<Throwable, String>

}