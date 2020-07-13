package es.gob.radarcovid.common.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import es.gob.radarcovid.RadarCovidApplication
import es.gob.radarcovid.common.di.module.ActivitiesModule
import es.gob.radarcovid.common.di.module.BroadcastReceiverModule
import es.gob.radarcovid.common.di.module.NetworkModule
import es.gob.radarcovid.common.di.module.RepositoryModule
import es.gob.radarcovid.common.di.scope.PerApplication
import javax.inject.Named

@PerApplication
@Component(
    modules = [NetworkModule::class,
        RepositoryModule::class,
        ActivitiesModule::class,
        BroadcastReceiverModule::class,
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