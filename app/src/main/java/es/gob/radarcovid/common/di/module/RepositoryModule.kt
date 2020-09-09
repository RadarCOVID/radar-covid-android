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
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.datamanager.repository.*

@Module
class RepositoryModule {


    @Provides
    @PerApplication
    fun providesSystemInfoRepository(repository: SystemInfoRepositoryImpl): SystemInfoRepository =
        repository

    @Provides
    @PerApplication
    fun providesBuildInfoRepository(repository: BuildInfoRepositoryImpl): BuildInfoRepository =
        repository

    @Provides
    @PerApplication
    fun providesExposureStatusRepository(repository: ExposureStatusRepositoryImpl): ExposureStatusRepository =
        repository

    @Provides
    @PerApplication
    fun providesDomainRepository(repository: DomainRepositoryImpl): DomainRepository = repository

    @Provides
    @PerApplication
    fun providesPreferencesRepository(repository: PreferencesRepositoryImpl): PreferencesRepository =
        repository

    @Provides
    @PerApplication
    fun providesExampleRepository(repository: ExampleRepositoryImpl): ExampleRepository = repository

    @Provides
    @PerApplication
    fun providesApiRepository(repository: ApiRepositoryImpl): ApiRepository = repository

}