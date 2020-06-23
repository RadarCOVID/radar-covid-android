package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ClearExposureDataUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun clearExposureData() = repository.clearData()

}