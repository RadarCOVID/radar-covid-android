package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.models.response.ResponseSettings
import com.indra.coronaradar.models.response.ResponseUuid
import org.funktionale.either.Either

interface ApiRepository {

    fun getUuid():Either<Throwable, ResponseUuid>

    fun getSettings():Either<Throwable, ResponseSettings>

}