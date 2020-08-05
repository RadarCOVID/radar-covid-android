package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ExposureRadarUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun setRadarEnabled(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit
    ) = repository.startRadar(onSuccess, onError, onCancelled)


    fun setRadarDisabled() = repository.stopRadar()

    fun isRadarEnabled() = repository.isRadarEnabled()

}