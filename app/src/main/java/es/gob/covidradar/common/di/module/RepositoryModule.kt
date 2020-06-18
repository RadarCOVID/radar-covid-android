package es.gob.covidradar.common.di.module

import es.gob.covidradar.common.di.scope.PerApplication
import es.gob.covidradar.datamanager.repository.*
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

    @Provides
    @PerApplication
    fun providesApiRepository(repository: ApiRepositoryImpl): ApiRepository = repository

}