package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(
    private val repository: ContactTracingRepository
) {

    fun reportInfected(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        //TODO REVIEW AUTH CODE
        repository.notifyInfected("", onSuccess, onError)
    }

}