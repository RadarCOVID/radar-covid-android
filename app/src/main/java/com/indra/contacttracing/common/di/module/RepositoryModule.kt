package com.indra.contacttracing.common.di.module

import com.indra.contacttracing.common.di.scope.PerApplication
import com.indra.contacttracing.datamanager.repository.ExampleRepository
import com.indra.contacttracing.datamanager.repository.ExampleRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun providesExampleRepository(repository: ExampleRepositoryImpl): ExampleRepository = repository

}