/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import es.gob.radarcovid.RadarCovidApplication
import es.gob.radarcovid.common.di.module.*
import es.gob.radarcovid.common.di.scope.PerApplication
import javax.inject.Named

@PerApplication
@Component(
    modules = [NetworkModule::class,
        EncryptedSharedPreferencesModule::class,
        RepositoryModule::class,
        ActivitiesModule::class,
        ViewsModule::class,
        ServicesModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<RadarCovidApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@Named("applicationContext") context: Context): Builder

        fun build(): ApplicationComponent

    }

}