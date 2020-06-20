package es.gob.covidradar.datamanager.repository

import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.models.domain.ExposureInfo
import es.gob.covidradar.models.domain.Settings

@PerActivity
interface ContactTracingRepository {

    fun checkGaenAvailability(callback: (Boolean) -> Unit)

    fun updateTracingSettings(settings: Settings)

    fun startRadar(onSuccess: () -> Unit, onError: (Exception) -> Unit, onCancelled: () -> Unit)

    fun stopRadar()

    fun isRadarEnabled(): Boolean

    fun syncData()

    fun clearData()

    fun getExposureInfo(): ExposureInfo

    fun notifyInfected(authCode: String, onSuccess: () -> Unit, onError: (Throwable) -> Unit)

}