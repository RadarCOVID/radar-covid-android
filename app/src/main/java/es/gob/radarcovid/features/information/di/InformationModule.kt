/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.information.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.radarcovid.features.information.presenter.InformationPresenterImpl
import es.gob.radarcovid.features.information.protocols.InformationPresenter
import es.gob.radarcovid.features.information.protocols.InformationRouter
import es.gob.radarcovid.features.information.protocols.InformationView
import es.gob.radarcovid.features.information.router.InformationRouterImpl
import es.gob.radarcovid.features.information.view.InformationActivity

@Module
class InformationModule {

    @Provides
    fun providesContext(activity: InformationActivity): Context = activity

    @Provides
    fun providesActivity(activity: InformationActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: InformationActivity): InformationView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: InformationPresenterImpl): InformationPresenter = presenter

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

    @Provides
    @PerActivity
    fun providesRouter(router: InformationRouterImpl): InformationRouter = router
}