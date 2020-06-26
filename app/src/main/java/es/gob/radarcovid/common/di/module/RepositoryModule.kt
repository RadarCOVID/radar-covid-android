package es.gob.radarcovid.common.di.module

import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.datamanager.repository.*
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
    fun providesRawRepository(repository: RawRepositoryImpl): RawRepository = repository

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