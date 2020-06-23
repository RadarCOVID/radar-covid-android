package es.gob.radarcovid.datamanager.repository

import org.funktionale.either.Either

interface ExampleRepository {

    fun exampleRequest(): Either<Throwable, String>

}