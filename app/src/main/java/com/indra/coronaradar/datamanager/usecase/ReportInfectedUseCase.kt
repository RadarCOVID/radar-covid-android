package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ReportInfectedUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun reportInfected(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        //TODO REVIEW AUTH CODE
        repository.notifyInfected("", onSuccess, onError)
    }

}