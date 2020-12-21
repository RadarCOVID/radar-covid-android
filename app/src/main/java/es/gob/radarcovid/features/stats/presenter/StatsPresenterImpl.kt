/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.stats.presenter

import es.gob.radarcovid.common.extensions.toDateFormat
import es.gob.radarcovid.datamanager.usecase.GetStatsUseCase
import es.gob.radarcovid.features.stats.protocols.StatsPresenter
import es.gob.radarcovid.features.stats.protocols.StatsView
import es.gob.radarcovid.models.domain.StatsRegionData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class StatsPresenterImpl @Inject constructor(
    private val view: StatsView,
    private val getStatsUseCase: GetStatsUseCase
) : StatsPresenter {

    private lateinit var countries: List<String>

    override fun viewReady() {
        requestCountries()
    }

    override fun onCountriesClick() {
        view.showCountriesDialog(countries)
    }

    private fun requestCountries() {
        getStatsUseCase.getStatsDataObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }

    private fun onSuccess(data: StatsRegionData) {
        this.countries = data.countries.map { it.name }
        val lastStatsDate = data.statsData.map { x -> x.date }.max() ?: Date()
        val lastStatsData = data.statsData.find { x -> x.date == lastStatsDate }

        if (lastStatsData != null) {
            view.setDataView(
                countries.size.toString(),
                lastStatsDate.toDateFormat(),
                String.format("%,d", lastStatsData.applicationsDownloads.totalAcummulated),
                String.format("%,d", lastStatsData.communicatedContagions.totalAcummulated)
            )
        }
    }

    private fun onError(error: Throwable) {
        view.showError(error, true)
    }

}