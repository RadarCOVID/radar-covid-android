package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.models.domain.ExpositionInfo

@PerActivity
interface ContactTracingRepository {

    fun startRadar(onSuccess: () -> Unit, onError: (Exception) -> Unit, onCancelled: () -> Unit)

    fun stopRadar()

    fun syncData()

    fun clearData()

    fun getExpositionInfo(): ExpositionInfo

    fun notifyInfected(authCode: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)

}