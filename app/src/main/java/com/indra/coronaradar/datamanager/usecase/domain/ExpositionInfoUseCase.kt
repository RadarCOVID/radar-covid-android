package com.indra.coronaradar.datamanager.usecase.domain

import com.indra.coronaradar.datamanager.repository.DomainRepository
import com.indra.coronaradar.models.domain.ExpositionInfo
import javax.inject.Inject

class ExpositionInfoUseCase @Inject constructor(private val repository: DomainRepository) {

    fun getExpositionInfo() = repository.expositionInfo

    fun setExpositionInfo(expositionInfo: ExpositionInfo) {
        repository.expositionInfo = expositionInfo
    }

}