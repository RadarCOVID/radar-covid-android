/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.radarcovid.features.covidreport.form.presenter.CovidReportPresenterImpl
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportRouter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import es.gob.radarcovid.features.covidreport.form.router.CovidReportRouterImpl
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import dagger.Module
import dagger.Provides

@Module
class CovidReportModule {

    @Provides
    fun providesContext(activity: CovidReportActivity): Context = activity

    @Provides
    fun providesActivity(activity: CovidReportActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: CovidReportActivity): CovidReportView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: CovidReportPresenterImpl): CovidReportPresenter = presenter

    @Provides
    @PerActivity
    fun providesCovidReportRouter(router: CovidReportRouterImpl): CovidReportRouter = router

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}