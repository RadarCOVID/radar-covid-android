package com.indra.contacttracing.common.di.component

import com.indra.contacttracing.ContactTracingApplication
import com.indra.contacttracing.common.di.module.ActivitiesModule
import com.indra.contacttracing.common.di.scope.PerApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [ActivitiesModule::class, AndroidSupportInjectionModule::class])
interface ApplicationComponent : AndroidInjector<ContactTracingApplication> {
}