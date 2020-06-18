package es.gob.covidradar.common.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import es.gob.covidradar.CovidRadarApplication
import es.gob.covidradar.common.di.module.ActivitiesModule
import es.gob.covidradar.common.di.module.NetworkModule
import es.gob.covidradar.common.di.module.RepositoryModule
import es.gob.covidradar.common.di.scope.PerApplication
import javax.inject.Named

@PerApplication
@Component(
    modules = [NetworkModule::class,
        RepositoryModule::class,
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<CovidRadarApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@Named("applicationContext") context: Context): Builder

        fun build(): ApplicationComponent

    }

}