package es.gob.radarcovid.datamanager.usecase.domain

import es.gob.radarcovid.datamanager.repository.DomainRepository
import es.gob.radarcovid.models.domain.ExposureInfo
import javax.inject.Inject

class DomainInfoUseCase @Inject constructor(private val repository: DomainRepository) {

    fun getExposureInfo() = repository.exposureInfo

    fun setExposureInfo(exposureInfo: ExposureInfo) {
        repository.exposureInfo = exposureInfo
    }

}