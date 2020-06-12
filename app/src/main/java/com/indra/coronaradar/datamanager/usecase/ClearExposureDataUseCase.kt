package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ClearExposureDataUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun clearExposureData() = repository.clearData()

}