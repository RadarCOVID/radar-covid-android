/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.covidreport.form.pages.step0.view.Step0MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.pages.step0.di.Step0MyHealthModule
import es.gob.radarcovid.features.covidreport.form.pages.step1.view.Step1MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.pages.step1.di.Step1MyHealthModule
import es.gob.radarcovid.features.covidreport.form.pages.step2.di.Step2MyHealthModule
import es.gob.radarcovid.features.covidreport.form.pages.step2.view.Step2MyHealthFragment

@Module
abstract class CovidReportFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [Step0MyHealthModule::class])
    abstract fun bindsStep0Fragments(): Step0MyHealthFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [Step1MyHealthModule::class])
    abstract fun bindsStep1Fragments(): Step1MyHealthFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [Step2MyHealthModule::class])
    abstract fun bindsStep2Fragments(): Step2MyHealthFragment
}