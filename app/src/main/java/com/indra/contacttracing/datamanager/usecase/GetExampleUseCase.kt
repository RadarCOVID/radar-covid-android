package com.indra.contacttracing.datamanager.usecase

import com.indra.contacttracing.common.base.asyncRequest
import com.indra.contacttracing.common.base.mapperScope
import com.indra.contacttracing.datamanager.repository.ExampleRepository
import com.indra.contacttracing.datamanager.repository.PreferencesRepository
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