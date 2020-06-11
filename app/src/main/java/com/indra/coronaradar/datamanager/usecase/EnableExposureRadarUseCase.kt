package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class EnableExposureRadarUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun setRadarEnabled(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit
    ) = repository.startRadar(onSuccess, onError, onCancelled)


    fun setRadarDisabled() = repository.stopRadar()

    fun isRadarEnabled() = repository.isRadarEnabled()

}