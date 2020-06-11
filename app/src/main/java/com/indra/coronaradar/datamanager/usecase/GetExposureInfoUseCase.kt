package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import com.indra.coronaradar.models.domain.ExposureInfo
import javax.inject.Inject

class GetExposureInfoUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun getExposureInfo(): ExposureInfo = repository.getExposureInfo()

}