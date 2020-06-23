package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.SystemInfoRepository
import javax.inject.Inject

class GetInternetInfoUseCase @Inject constructor(private val repository: SystemInfoRepository) {

    fun isInternetAvailable(): Boolean = repository.isInternetAvailable()

}