package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class SyncExposureDataUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun syncExposureData() = repository.syncData()

}