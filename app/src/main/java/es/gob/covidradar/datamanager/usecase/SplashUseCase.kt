package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.common.base.asyncRequest
import es.gob.covidradar.common.base.mapperScope
import es.gob.covidradar.datamanager.mapper.SettingsDataMapper
import es.gob.covidradar.datamanager.repository.ApiRepository
import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import es.gob.covidradar.datamanager.repository.PreferencesRepository
import es.gob.covidradar.models.domain.Settings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import org.funktionale.either.Either
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

    private fun getSettings(onSuccess: (Settings) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            val res = apiRepository.getSettings()
            mapperScope {
                settingsDataMapper.transform(res.right().get())
            }
        }

    }

    fun getUuid(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) =
        asyncRequest(onSuccess, onError) {
            val response = apiRepository.getUuid()
            if (response.isRight())
                Either.right(response.right().get().uuid)
            else
                Either.left(response.left().get())
        }

    fun isUuidInitialized() = preferencesRepository.getUuid().isNotEmpty()

    fun persistUuid(uuid: String) = preferencesRepository.setUuid(uuid)

    fun updateTracingSettings(settings: Settings) =
        contactTracingRepository.updateTracingSettings(settings)

    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        contactTracingRepository.checkGaenAvailability(callback)

}