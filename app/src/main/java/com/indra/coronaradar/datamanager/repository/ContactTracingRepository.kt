package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.models.domain.ExposureInfo

@PerActivity
interface ContactTracingRepository {

    fun checkGaenAvailability(callback: (Boolean) -> Unit)

    fun startRadar(onSuccess: () -> Unit, onError: (Exception) -> Unit, onCancelled: () -> Unit)

    fun stopRadar()

    fun isRadarEnabled(): Boolean

    fun syncData()

    fun clearData()

    fun getExposureInfo(): ExposureInfo

    fun notifyInfected(authCode: String, onSuccess: () -> Unit, onError: (Throwable) -> Unit)

}