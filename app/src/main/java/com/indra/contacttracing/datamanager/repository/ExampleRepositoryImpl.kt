package com.indra.contacttracing.datamanager.repository

import com.indra.contacttracing.common.base.BaseRepository
import com.indra.contacttracing.datamanager.api.ApiInterface
import org.funktionale.either.Either
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ExampleRepository {

    override fun exampleRequest(): Either<Throwable, String> = callService {
        apiInterface.test()
    }

}