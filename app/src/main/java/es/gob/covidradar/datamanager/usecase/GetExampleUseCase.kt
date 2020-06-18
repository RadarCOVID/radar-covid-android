package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.common.base.asyncRequest
import es.gob.covidradar.common.base.mapperScope
import es.gob.covidradar.datamanager.repository.ExampleRepository
import es.gob.covidradar.datamanager.repository.PreferencesRepository
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