package com.indra.contacttracing.common.di.module

import com.indra.contacttracing.common.di.scope.PerApplication
import com.indra.contacttracing.datamanager.repository.ExampleRepository
import com.indra.contacttracing.datamanager.repository.ExampleRepositoryImpl
import com.indra.contacttracing.datamanager.repository.PreferencesRepository
import com.indra.contacttracing.datamanager.repository.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun providesPreferencesRepository(repository: PreferencesRepositoryImpl): PreferencesRepository =
        repository

    @Provides
    @PerApplication
    fun providesExampleRepository(repository: ExampleRepositoryImpl): ExampleRepository = repository

}