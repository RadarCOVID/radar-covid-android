package com.indra.contacttracing.datamanager.usecase.domain

import com.indra.contacttracing.datamanager.repository.DomainRepository
import com.indra.contacttracing.models.domain.ExpositionInfo
import javax.inject.Inject

class ExpositionInfoUseCase @Inject constructor(private val repository: DomainRepository) {

    fun getExpositionInfo() = repository.expositionInfo

    fun setExpositionInfo(expositionInfo: ExpositionInfo) {
        repository.expositionInfo = expositionInfo
    }

}