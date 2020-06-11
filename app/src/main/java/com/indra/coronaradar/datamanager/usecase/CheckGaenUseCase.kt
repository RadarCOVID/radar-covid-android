package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class CheckGaenUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        repository.checkGaenAvailability(callback)

}