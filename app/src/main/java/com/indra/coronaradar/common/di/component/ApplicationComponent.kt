package com.indra.coronaradar.common.di.component

import android.content.Context
import com.indra.coronaradar.ContactTracingApplication
import com.indra.coronaradar.common.di.module.ActivitiesModule
import com.indra.coronaradar.common.di.module.NetworkModule
import com.indra.coronaradar.common.di.module.RepositoryModule
import com.indra.coronaradar.common.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named

@PerApplication
@Component(
    modules = [NetworkModule::class,
        RepositoryModule::class,
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<ContactTracingApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@Named("applicationContext") context: Context): Builder

        fun build(): ApplicationComponent

    }

}