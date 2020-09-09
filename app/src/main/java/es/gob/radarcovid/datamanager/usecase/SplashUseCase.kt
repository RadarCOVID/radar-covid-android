/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.LanguagesDataMapper
import es.gob.radarcovid.datamanager.mapper.RegionsDataMapper
import es.gob.radarcovid.datamanager.mapper.SettingsDataMapper
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.BuildInfoRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.InitializationData
import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.Region
import es.gob.radarcovid.models.domain.Settings
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val contactTracingRepository: ContactTracingRepository,
    private val settingsDataMapper: SettingsDataMapper,
    private val languagesDataMapper: LanguagesDataMapper,
    private val regionsDataMapper: RegionsDataMapper,
    private val buildInfoRepository: BuildInfoRepository
) {

    fun getVersionCode(): Int = buildInfoRepository.getVersionCode()

    fun getInitializationObservable(): Observable<InitializationData> =
        getUuidObservable().flatMap {
            Observable.zip<Settings, Map<String, String>, List<Language>, List<Region>, InitializationData>(
                getSettingsObservable(),
                getLabelsObservable(),
                getLanguagesObservable(),
                getRegionsObservable(),
                Function4 { settings, labels, languages, regions ->
                    InitializationData(settings, labels, languages, regions)
                })
                .map { initializationData ->

                    preferencesRepository.setHealingTime(initializationData.settings.healingTime)

                    if (initializationData.labels.isNotEmpty()) {
                        preferencesRepository.setLabels(initializationData.labels)
                    }

                    if (initializationData.languages.isNotEmpty()) {
                        preferencesRepository.setLanguages(initializationData.languages)
                    }

                    if (initializationData.regions.isNotEmpty()) {
                        preferencesRepository.setRegions(initializationData.regions)
                    }

                    contactTracingRepository.updateTracingSettings(initializationData.settings)

                    initializationData

                }
        }

    private fun getUuidObservable(): Observable<String> = Observable.create { emitter ->
        if (isUuidInitialized()) {
            emitter.onNext(preferencesRepository.getUuid())
            emitter.onComplete()
        } else {
            getUuid(
                onSuccess = {
                    if (it.isNotEmpty()) {
                        preferencesRepository.setUuid(it)
                    }
                    emitter.onNext(it)
                    emitter.onComplete()
                },
                onError = {
                    emitter.onError(it)
                    emitter.onComplete()
                })
        }
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

    private fun getLanguagesObservable(): Observable<List<Language>> =
        Observable.create { emitter ->
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

    private fun getRegionsObservable(): Observable<List<Region>> = Observable.create { emitter ->
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


    private fun getLanguages(onSuccess: (List<Language>) -> Unit, onError: (Throwable) -> Unit) {
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

    private fun getRegions(onSuccess: (List<Region>) -> Unit, onError: (Throwable) -> Unit) {
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


    fun checkGaenAvailability(callback: (Boolean) -> Unit) =
        contactTracingRepository.checkGaenAvailability(callback)

}