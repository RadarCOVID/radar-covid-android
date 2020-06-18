package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class CheckGaenUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        repository.checkGaenAvailability(callback)

}