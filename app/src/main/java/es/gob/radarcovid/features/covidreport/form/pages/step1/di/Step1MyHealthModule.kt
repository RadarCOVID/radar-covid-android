/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step1.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.covidreport.form.pages.step1.view.Step1MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.pages.step1.presenter.Step1MyHealthPresenterImp
import es.gob.radarcovid.features.covidreport.form.pages.step1.protocols.Step1MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step1.protocols.Step1MyHealthView

@Module
class Step1MyHealthModule {

    @Provides
    fun providesFragment(fragment: Step1MyHealthFragment): Fragment = fragment

    @Provides
    @PerFragment
    fun providesView(view: Step1MyHealthFragment): Step1MyHealthView = view

    @Provides
    @PerFragment
    fun providesPresenter(presenter: Step1MyHealthPresenterImp): Step1MyHealthPresenter = presenter

}