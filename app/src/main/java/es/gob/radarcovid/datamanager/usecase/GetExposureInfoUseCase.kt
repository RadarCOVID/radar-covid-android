package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.models.domain.ExposureInfo
import javax.inject.Inject

class GetExposureInfoUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun getExposureInfo(): ExposureInfo = repository.getExposureInfo()

}