package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class SyncExposureDataUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun syncExposureData() = repository.syncData()

}