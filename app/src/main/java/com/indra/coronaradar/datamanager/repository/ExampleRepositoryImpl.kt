package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.common.base.BaseRepository
import com.indra.coronaradar.datamanager.api.ApiInterface
import org.funktionale.either.Either
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ExampleRepository {

    override fun exampleRequest(): Either<Throwable, String> = callService {
        apiInterface.test()
    }

}