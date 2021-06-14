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

import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.RegionsDataMapper
import es.gob.radarcovid.datamanager.mapper.StatsDataMapper
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.Region
import es.gob.radarcovid.models.domain.StatsData
import es.gob.radarcovid.models.domain.StatsRegionData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

class GetStatsUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val regionsDataMapper: RegionsDataMapper,
    private val statsDataMapper: StatsDataMapper
) {

    fun getStatsDataObservable(): Observable<StatsRegionData> =
        Observable.zip<List<Region>, List<StatsData>, StatsRegionData>(
            getCountriesObservable(),
            getStatsObservable(),
            BiFunction { countries, data ->
                StatsRegionData(data, countries)
            })

    private fun getCountriesObservable(): Observable<List<Region>> =
        Observable.create { emitter ->
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

    private fun getStatsObservable(): Observable<List<StatsData>> =
        Observable.create { emitter ->
            getStats(
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


    private fun getRegions(onSuccess: (List<Region>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(
                apiRepository.getCountries(
                    preferencesRepository.getSelectedLanguage(),
                    Constants.SO_NAME,
                    BuildConfig.VERSION_NAME
                )
            ) {
                regionsDataMapper.transform(it)
            }
        }
    }

    private fun getStats(onSuccess: (List<StatsData>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(
                apiRepository.getStats()
            ) {
                statsDataMapper.transform(it)
            }
        }
    }
}