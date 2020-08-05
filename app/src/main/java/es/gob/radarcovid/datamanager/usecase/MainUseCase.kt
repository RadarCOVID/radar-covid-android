package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val contactTracingRepository: ContactTracingRepository
) {

    fun isPollCompleted(): Boolean = preferencesRepository.isPollCompleted()

    fun syncExposureInfo() = contactTracingRepository.syncData()

}