package com.indra.coronaradar.datamanager.usecase.domain

import com.indra.coronaradar.datamanager.repository.DomainRepository
import com.indra.coronaradar.models.domain.ExposureInfo
import javax.inject.Inject

class DomainInfoUseCase @Inject constructor(private val repository: DomainRepository) {

    fun getExposureInfo() = repository.exposureInfo

    fun setExposureInfo(exposureInfo: ExposureInfo) {
        repository.exposureInfo = exposureInfo
    }

}