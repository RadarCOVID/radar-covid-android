package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.SystemInfoRepository
import javax.inject.Inject

class GetInternetInfoUseCase @Inject constructor(private val repository: SystemInfoRepository) {

    fun isInternetAvailable(): Boolean = repository.isInternetAvailable()

}