/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step0.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.covidreport.form.pages.step0.presenter.Step0MyHealthPresenterImp
import es.gob.radarcovid.features.covidreport.form.pages.step0.protocols.Step0MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step0.view.Step0MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.pages.step0.protocols.Step0MyHealthView

@Module
class Step0MyHealthModule {

    @Provides
    fun providesFragment(fragment: Step0MyHealthFragment): Fragment = fragment

    @Provides
    @PerFragment
    fun providesView(fragment: Step0MyHealthFragment): Step0MyHealthView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: Step0MyHealthPresenterImp): Step0MyHealthPresenter = presenter
}