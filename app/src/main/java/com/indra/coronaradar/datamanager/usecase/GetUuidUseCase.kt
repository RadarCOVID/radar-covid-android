package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.common.base.asyncRequest
import com.indra.coronaradar.datamanager.repository.ApiRepository
import com.indra.coronaradar.datamanager.repository.PreferencesRepository
import org.funktionale.either.Either
import javax.inject.Inject

class GetUuidUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository
) {

    fun getUuid(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) =
        asyncRequest(onSuccess, onError) {
            val response = apiRepository.getUuid()
            if (response.isRight())
                Either.right(response.right().get().uuid)
            else
                Either.left(response.left().get())
        }

    fun isUuidInitialized() = preferencesRepository.getUuid().isNotEmpty()

    fun persistUuid(uuid: String) = preferencesRepository.setUuid(uuid)

}