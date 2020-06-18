package es.gob.covidradar.datamanager.usecase.domain

import es.gob.covidradar.datamanager.repository.DomainRepository
import es.gob.covidradar.models.domain.ExposureInfo
import javax.inject.Inject

class DomainInfoUseCase @Inject constructor(private val repository: DomainRepository) {

    fun getExposureInfo() = repository.exposureInfo

    fun setExposureInfo(exposureInfo: ExposureInfo) {
        repository.exposureInfo = exposureInfo
    }

}