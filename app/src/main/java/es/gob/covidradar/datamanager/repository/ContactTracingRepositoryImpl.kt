package es.gob.covidradar.datamanager.repository

import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.datamanager.mapper.ExpositionInfoDataMapper
import es.gob.covidradar.models.domain.ExposureInfo
import es.gob.covidradar.models.domain.Settings
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.GaenAvailability
import org.dpppt.android.sdk.backend.ResponseCallback
import org.dpppt.android.sdk.internal.nearby.GoogleExposureClient
import org.dpppt.android.sdk.models.ExposeeAuthMethodAuthorization
import java.util.*
import javax.inject.Inject

@PerActivity
class ContactTracingRepositoryImpl @Inject constructor(
    private val expositionInfoDataMapper: ExpositionInfoDataMapper,
    private val activity: AppCompatActivity
) : ContactTracingRepository {

    override fun checkGaenAvailability(callback: (Boolean) -> Unit) {
        DP3T.checkGaenAvailability(activity) { availability ->
            val result = when (availability) {
                GaenAvailability.AVAILABLE -> true
                else -> false
            }
            callback(result)
        }
    }

    override fun updateTracingSettings(settings: Settings) {
        with(settings.exposureConfiguration.attenuation) {
            GoogleExposureClient.getInstance(activity)
                .setParams(riskLevelValue[0], riskLevelValue[1])
        }
    }

    override fun startRadar(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit
    ) {
        DP3T.start(activity,
            { onSuccess() },
            { exception -> onError(exception) },
            { onCancelled.invoke() })
    }

    override fun stopRadar() {
        DP3T.stop(activity)
    }

    override fun isRadarEnabled(): Boolean = DP3T.isTracingEnabled(activity)

    override fun syncData() {
        DP3T.sync(activity)
    }

    override fun clearData() {
        DP3T.clearData(activity)
    }

    override fun getExposureInfo(): ExposureInfo =
        expositionInfoDataMapper.transform(DP3T.getStatus(activity))

    override fun notifyInfected(
        authCode: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        //TODO: REVIEW ONSET DATE AND AUTH CODE MANAGEMENT
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -14)
        DP3T.sendIAmInfected(activity, calendar.time, ExposeeAuthMethodAuthorization(authCode),
            object : ResponseCallback<Void> {
                override fun onSuccess(response: Void?) {
                    onSuccess()
                }

                override fun onError(throwable: Throwable?) {
                    onError(throwable ?: Exception("Error notifying infection"))
                }

            })
    }
}