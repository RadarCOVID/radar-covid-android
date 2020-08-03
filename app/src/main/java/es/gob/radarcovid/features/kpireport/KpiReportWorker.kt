package es.gob.radarcovid.features.kpireport

import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.android.gms.nearby.exposurenotification.ExposureSummary
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.extensions.toJson
import es.gob.radarcovid.common.extensions.toTimeStamp
import es.gob.radarcovid.datamanager.api.ApiInterface
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.ApiRepositoryImpl
import es.gob.radarcovid.models.request.RequestKpi
import es.gob.radarcovid.models.request.RequestKpiReport
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.dpppt.android.sdk.internal.AppConfigManager
import org.dpppt.android.sdk.internal.nearby.GoogleExposureClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KpiReportWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {

        const val TAG = "KpiReportWorker"

        fun start(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val work = PeriodicWorkRequest.Builder(KpiReportWorker::class.java, 12, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.KEEP, work)
        }

    }

    private val apiRepository: ApiRepository = ApiRepositoryImpl(getApiInterface())

    override fun doWork(): Result {

        val kpiRequest = collectKpi(applicationContext)
        Log.d(TAG, kpiRequest.toJson())
        return if (kpiRequest != null) {
            val response = apiRepository.postKpiReport(kpiRequest)
            if (response.isRight())
                Result.success()
            else
                Result.failure()
        } else {
            Result.failure()
        }

    }

    private fun getApiInterface(): ApiInterface = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(getHttpClient())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    private fun getHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun collectKpi(context: Context): RequestKpiReport? {
        val lastLoadedTimes = AppConfigManager.getInstance(context).lastLoadedTimes
        return lastLoadedTimes.maxBy { it.key.startOfDayTimestamp }?.let {
            val timeStamp = Date(it.key.startOfDayTimestamp).toTimeStamp()
            val token = it.key.formatAsString()
            runBlocking {
                suspendCoroutine<RequestKpiReport?> { continuation ->
                    GoogleExposureClient.getInstance(context)
                        .getExposureSummary(token).addOnCompleteListener { task ->
                            if (task.isSuccessful)
                                continuation.resume(transform(task.result, timeStamp))
                            else
                                continuation.resume(null)
                        }
                }
            }
        }
    }

    private fun transform(exposureSummary: ExposureSummary, timeStamp: String): RequestKpiReport? =
        if (exposureSummary.attenuationDurationsInMinutes.size == 3)
            RequestKpiReport().apply {
                add(
                    RequestKpi(
                        kpi = RequestKpi.ATTENUATION_DURATIONS_1,
                        timestamp = timeStamp,
                        value = exposureSummary.attenuationDurationsInMinutes[0]
                    )
                )
                add(
                    RequestKpi(
                        kpi = RequestKpi.ATTENUATION_DURATIONS_2,
                        timestamp = timeStamp,
                        value = exposureSummary.attenuationDurationsInMinutes[1]
                    )
                )
                add(
                    RequestKpi(
                        kpi = RequestKpi.ATTENUATION_DURATIONS_3,
                        timestamp = timeStamp,
                        value = exposureSummary.attenuationDurationsInMinutes[2]
                    )
                )
                add(
                    RequestKpi(
                        kpi = RequestKpi.DAYS_SINCE_LAST_EXPOSURE,
                        timestamp = timeStamp,
                        value = exposureSummary.daysSinceLastExposure
                    )
                )
                add(
                    RequestKpi(
                        kpi = RequestKpi.MATCHED_KEY_COUNT,
                        timestamp = timeStamp,
                        value = exposureSummary.matchedKeyCount
                    )
                )
                add(
                    RequestKpi(
                        kpi = RequestKpi.MAXIMUM_RISK_SCORE,
                        timestamp = timeStamp,
                        value = exposureSummary.maximumRiskScore
                    )
                )
                add(
                    RequestKpi(
                        kpi = RequestKpi.SUMMATION_RISK_SCORE,
                        timestamp = timeStamp,
                        value = exposureSummary.summationRiskScore
                    )
                )
            }
        else
            null

}