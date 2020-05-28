package com.indra.contacttracing.common.di.component

import com.indra.contacttracing.ContactTracingApplication
import com.indra.contacttracing.common.di.module.ActivitiesModule
import com.indra.contacttracing.common.di.module.NetworkModule
import com.indra.contacttracing.common.di.module.RepositoryModule
import com.indra.contacttracing.common.di.scope.PerApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [NetworkModule::class,
        RepositoryModule::class,
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<ContactTracingApplication> {
}