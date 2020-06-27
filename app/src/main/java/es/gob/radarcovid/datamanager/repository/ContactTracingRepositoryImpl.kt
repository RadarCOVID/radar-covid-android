package es.gob.radarcovid.datamanager.repository

import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.datamanager.mapper.ExposureInfoDataMapper
import es.gob.radarcovid.models.domain.ExposureInfo
import es.gob.radarcovid.models.domain.Settings
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.GaenAvailability
import org.dpppt.android.sdk.backend.ResponseCallback
import org.dpppt.android.sdk.internal.AppConfigManager
import org.dpppt.android.sdk.internal.ExposureDayStorage
import org.dpppt.android.sdk.models.ExposeeAuthMethodAuthorization
import java.util.*
import javax.inject.Inject

@PerActivity
class ContactTracingRepositoryImpl @Inject constructor(
    private val exposureInfoDataMapper: ExposureInfoDataMapper,
    private val activity: AppCompatActivity
) : ContactTracingRepository {

    override fun checkGaenAvailability(callback: (Boolean) -> Unit) {
        if (BuildConfig.isMock) {
            callback(true)
        } else {
            DP3T.checkGaenAvailability(activity) { availability ->
                val result = when (availability) {
                    GaenAvailability.AVAILABLE -> true
                    else -> false
                }
                callback(result)
            }
        }
    }

    override fun updateTracingSettings(settings: Settings) {
        AppConfigManager.getInstance(activity).run {
            attenuationThresholdLow = settings.attenuationThresholdLow
            attenuationThresholdMedium = settings.attenuationThresholdMedium
        }
//        AppConfigManager.getInstance(activity).setExposureConfiguration(
//            ExposureConfiguration.ExposureConfigurationBuilder()
//                .setTransmissionRiskScores(*settings.exposureConfiguration.transmission.value)
//                .setTransmissionRiskWeight(settings.exposureConfiguration.transmission.weight.toInt())
//                .setDurationScores(*settings.exposureConfiguration.duration.value)
//                .setDurationWeight(settings.exposureConfiguration.duration.weight.toInt())
//                .setDaysSinceLastExposureScores(*settings.exposureConfiguration.days.value)
//                .setDaysSinceLastExposureWeight(settings.exposureConfiguration.days.weight.toInt())
//                .setAttenuationScores(*settings.exposureConfiguration.attenuation.value)
//                .setAttenuationWeight(settings.exposureConfiguration.attenuation.weight.toInt())
//                .setMinimumRiskScore(settings.minRiskScore)
//                .build()
//        )
    }

    override fun startRadar(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit
    ) {
        if (BuildConfig.isMock)
            onSuccess()
        else
            DP3T.start(activity,
                {
                    onSuccess()
                },
                { exception ->
                    onError(exception)
                },
                {
                    onCancelled.invoke()
                })
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

    override fun getExposureInfo(): ExposureInfo = exposureInfoDataMapper.transform(
        DP3T.getStatus(activity),
        ExposureDayStorage.getInstance(activity).exposureDays
    )

    override fun notifyInfected(
        authCode: String,
        onSet: Date,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (BuildConfig.isMock) {
            onSuccess()
        } else {
            DP3T.sendIAmInfected(activity, onSet, ExposeeAuthMethodAuthorization(authCode),
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
}