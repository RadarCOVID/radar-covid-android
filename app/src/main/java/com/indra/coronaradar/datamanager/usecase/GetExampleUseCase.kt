package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.common.base.asyncRequest
import com.indra.coronaradar.common.base.mapperScope
import com.indra.coronaradar.datamanager.repository.ExampleRepository
import com.indra.coronaradar.datamanager.repository.PreferencesRepository
import javax.inject.Inject

class GetExampleUseCase @Inject constructor(private val exampleRepository: ExampleRepository, private val preferencesRepository: PreferencesRepository) {

    fun getExample(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess = onSuccess, onError = onError) {
            mapperScope { //WRAP INTO mapperScope to do the map async
                exampleRepository.exampleRequest().right().get()
            }
        }
        preferencesRepository.setOnboardingCompleted(true)
    }

}