package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.common.base.BaseRepository
import com.indra.coronaradar.datamanager.api.ApiInterface
import com.indra.coronaradar.models.response.ResponseSettings
import com.indra.coronaradar.models.response.ResponseUuid
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

}