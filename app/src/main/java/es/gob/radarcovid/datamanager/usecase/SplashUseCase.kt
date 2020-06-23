package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.SettingsDataMapper
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.Settings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val contactTracingRepository: ContactTracingRepository,
    private val settingsDataMapper: SettingsDataMapper
) {

    fun getInitializationObservable(): Observable<Pair<Settings, String>> =
        Observable.zip<Settings, String, Pair<Settings, String>>(
            getSettingsObservable(),
            getUuidObservable(),
            BiFunction { settings, uuid -> Pair(settings, uuid) })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

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

    fun updateTracingSettings(settings: Settings) =
        contactTracingRepository.updateTracingSettings(settings)

    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        contactTracingRepository.checkGaenAvailability(callback)

}