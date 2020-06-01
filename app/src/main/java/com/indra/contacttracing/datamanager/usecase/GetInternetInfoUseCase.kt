package com.indra.contacttracing.datamanager.usecase

import com.indra.contacttracing.datamanager.repository.SystemInfoRepository
import javax.inject.Inject

class GetInternetInfoUseCase @Inject constructor(private val repository: SystemInfoRepository) {

    fun isInternetAvailable(): Boolean = repository.isInternetAvailable()

}