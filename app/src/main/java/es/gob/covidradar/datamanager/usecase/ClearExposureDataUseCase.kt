package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ClearExposureDataUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun clearExposureData() = repository.clearData()

}