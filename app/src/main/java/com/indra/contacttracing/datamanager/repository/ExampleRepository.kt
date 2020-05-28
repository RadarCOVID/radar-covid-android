package com.indra.contacttracing.datamanager.repository

import org.funktionale.either.Either

interface ExampleRepository {
    fun exampleRequest(): Either<Throwable, String>
}