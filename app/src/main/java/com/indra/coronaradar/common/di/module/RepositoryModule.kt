package com.indra.coronaradar.common.di.module

import com.indra.coronaradar.common.di.scope.PerApplication
import com.indra.coronaradar.datamanager.repository.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun providesSystemInfoRepository(repository: SystemInfoRepositoryImpl): SystemInfoRepository =
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

}