package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import es.gob.covidradar.models.domain.ExposureInfo
import javax.inject.Inject

class GetExposureInfoUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun getExposureInfo(): ExposureInfo = repository.getExposureInfo()

}