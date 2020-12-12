/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.helpline.di.HelplineModule
import es.gob.radarcovid.features.helpline.view.HelplineFragment
import es.gob.radarcovid.features.home.di.HomeModule
import es.gob.radarcovid.features.home.view.HomeFragment
import es.gob.radarcovid.features.mydata.di.MyDataModule
import es.gob.radarcovid.features.mydata.view.MyDataFragment
import es.gob.radarcovid.features.settings.di.SettingsModule
import es.gob.radarcovid.features.settings.view.SettingsFragment

@Module
abstract class MainFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindsHomeFragment(): HomeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [MyDataModule::class])
    abstract fun bindsMyDataFragment(): MyDataFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [HelplineModule::class])
    abstract fun bindsHelplineFragment(): HelplineFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [SettingsModule::class, SettingsFragmentsModule::class])
    abstract fun bindsSettingsFragment(): SettingsFragment

}