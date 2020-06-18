package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.datamanager.repository.SystemInfoRepository
import javax.inject.Inject

class GetInternetInfoUseCase @Inject constructor(private val repository: SystemInfoRepository) {

    fun isInternetAvailable(): Boolean = repository.isInternetAvailable()

}