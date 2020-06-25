package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val contactTracingRepository: ContactTracingRepository,
    private val preferencesRepository: PreferencesRepository
) {

    fun isPollCompleted(): Boolean = preferencesRepository.isPollCompleted()

    fun syncExposureData() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                contactTracingRepository.syncData()
            }
        }
    }

}