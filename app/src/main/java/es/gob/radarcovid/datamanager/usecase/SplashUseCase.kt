package es.gob.radarcovid.datamanager.usecase

import android.util.Log
import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.SettingsDataMapper
import es.gob.radarcovid.datamanager.repository.*
import es.gob.radarcovid.models.domain.InitializationData
import es.gob.radarcovid.models.domain.Settings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val contentfulRepository: ContentfulRepository,
    private val preferencesRepository: PreferencesRepository,
    private val contactTracingRepository: ContactTracingRepository,
    private val settingsDataMapper: SettingsDataMapper,
    private val systemInfoRepository: SystemInfoRepository
) {

    fun getVersionCode(): Int = systemInfoRepository.getVersionCode()

    fun getInitializationObservable(): Observable<InitializationData> =
        Observable.zip<String, Settings, Map<String, String>, InitializationData>(
            getUuidObservable(),
            getSettingsObservable(),
            getLabelsObservable(),
            Function3 { uuid, settings, labels ->
                Log.d("asa", "asdasd")
                InitializationData(uuid, settings, labels)
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

    private fun getLabelsObservable(): Observable<Map<String, String>> =
        Observable.create { emitter ->
            getLabels(
                onSuccess = {
                    emitter.onNext(it)
                    emitter.onComplete()
                },
                onError = {
                    emitter.onError(it)
                    emitter.onComplete()
                }
            )
        }


    private fun getSettingsObservable(): Observable<Settings> =
        Observable.create { emitter ->
            getSettings(
                onSuccess = {
                    emitter.onNext(it)
                    emitter.onComplete()
                },
                onError = {
                    emitter.onError(it)
                    emitter.onComplete()
                })
        }

    private fun getUuidObservable(): Observable<String> = Observable.create { emitter ->
        if (isUuidInitialized()) {
            emitter.onNext("")
            emitter.onComplete()
        } else {
            getUuid(
                onSuccess = {
                    emitter.onNext(it)
                    emitter.onComplete()
                },
                onError = {
                    emitter.onError(it)
                    emitter.onComplete()
                })
        }
    }

    private fun getLabels(onSuccess: (Map<String, String>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            contentfulRepository.getLabels(
                preferencesRepository.getLanguage(),
                preferencesRepository.getRegion()
            )
        }
    }

    private fun getSettings(onSuccess: (Settings) -> Unit, onError: (Throwable) -> Unit) =
        asyncRequest(onSuccess, onError) {
            mapperScope(apiRepository.getSettings()) {
                settingsDataMapper.transform(it)
            }
        }

    private fun getUuid(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) =
        asyncRequest(onSuccess, onError) {
            mapperScope(apiRepository.getUuid()) {
                it.uuid
            }
        }

    fun isUuidInitialized() = preferencesRepository.getUuid().isNotEmpty()

    fun persistUuid(uuid: String) = preferencesRepository.setUuid(uuid)

    fun persistLabels(labels: Map<String, String>) = preferencesRepository.setLabels(labels)

    fun updateTracingSettings(settings: Settings) =
        contactTracingRepository.updateTracingSettings(settings)

    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        contactTracingRepository.checkGaenAvailability(callback)

}