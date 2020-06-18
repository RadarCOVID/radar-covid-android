package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun reportInfected(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        //TODO REVIEW AUTH CODE
        repository.notifyInfected("", onSuccess, onError)
    }

}