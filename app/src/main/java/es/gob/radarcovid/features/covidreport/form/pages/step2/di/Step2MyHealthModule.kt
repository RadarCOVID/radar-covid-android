/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step2.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.covidreport.form.pages.step2.presenter.Step2MyHealthPresenterImp
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthRouter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthView
import es.gob.radarcovid.features.covidreport.form.pages.step2.router.Step2MyHealthRouterImp
import es.gob.radarcovid.features.covidreport.form.pages.step2.view.Step2MyHealthFragment

@Module
class Step2MyHealthModule {

    @Provides
    fun providesFragment(fragment: Step2MyHealthFragment): Fragment = fragment

    @Provides
    @PerFragment
    fun providesView(view: Step2MyHealthFragment): Step2MyHealthView = view

    @Provides
    @PerFragment
    fun providesPresenter(presenter: Step2MyHealthPresenterImp): Step2MyHealthPresenter = presenter

    @Provides
    @PerFragment
    fun providesRouter(router: Step2MyHealthRouterImp): Step2MyHealthRouter = router
}