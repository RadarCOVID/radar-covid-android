package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.LanguagesDataMapper
import es.gob.radarcovid.datamanager.mapper.RegionsDataMapper
import es.gob.radarcovid.datamanager.mapper.SettingsDataMapper
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.datamanager.repository.SystemInfoRepository
import es.gob.radarcovid.models.domain.InitializationData
import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.Region
import es.gob.radarcovid.models.domain.Settings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val contactTracingRepository: ContactTracingRepository,
    private val settingsDataMapper: SettingsDataMapper,
    private val languagesDataMapper: LanguagesDataMapper,
    private val regionsDataMapper: RegionsDataMapper,
    private val systemInfoRepository: SystemInfoRepository
) {

    fun getVersionCode(): Int = systemInfoRepository.getVersionCode()

    fun getInitializationObservable(): Observable<InitializationData> =
        Observable.zip<Settings, Map<String, String>, List<Language>, List<Region>, InitializationData>(
            observeSettings(),
            observeLabels(),
            observeLanguages(),
            observeRegions(),
            Function4 { settings, labels, languages, regions ->
                InitializationData(settings, labels, languages, regions)
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

    fun observeUuid(): Observable<String> = Observable.create { emitter ->
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

    private fun observeSettings(): Observable<Settings> =
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

    private fun observeLabels(): Observable<Map<String, String>> =
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

    private fun observeLanguages(): Observable<List<Language>> = Observable.create { emitter ->
        getLanguages(
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

    private fun observeRegions(): Observable<List<Region>> = Observable.create { emitter ->
        getRegions(
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

    private fun getUuid(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) =
        asyncRequest(onSuccess, onError) {
            mapperScope(apiRepository.getUuid()) {
                it.uuid
            }
        }

    private fun getSettings(onSuccess: (Settings) -> Unit, onError: (Throwable) -> Unit) =
        asyncRequest(onSuccess, onError) {
            mapperScope(apiRepository.getSettings()) {
                settingsDataMapper.transform(it)
            }
        }

    private fun getLabels(onSuccess: (Map<String, String>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            apiRepository.getLabels(
                preferencesRepository.getUuid(),
                preferencesRepository.getSelectedLanguage(),
                preferencesRepository.getSelectedRegion()
            )
        }
    }


    fun getLanguages(onSuccess: (List<Language>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(
                apiRepository.getLanguages(
                    preferencesRepository.getUuid(),
                    preferencesRepository.getSelectedLanguage()
                )
            ) {
                languagesDataMapper.transform(it)
            }
        }
    }

    fun getRegions(onSuccess: (List<Region>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(
                apiRepository.getRegions(
                    preferencesRepository.getUuid(),
                    preferencesRepository.getSelectedLanguage()
                )
            ) {
                regionsDataMapper.transform(it)
            }
        }
    }

    fun isUuidInitialized() = preferencesRepository.getUuid().isNotEmpty()

    fun persistUuid(uuid: String) = preferencesRepository.setUuid(uuid)

    fun persistLabels(labels: Map<String, String>) = preferencesRepository.setLabels(labels)

    fun persistLanguages(languages: List<Language>) = preferencesRepository.setLanguages(languages)

    fun persistRegions(regions: List<Region>) = preferencesRepository.setRegions(regions)

    fun updateTracingSettings(settings: Settings) =
        contactTracingRepository.updateTracingSettings(settings)

    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        contactTracingRepository.checkGaenAvailability(callback)

}