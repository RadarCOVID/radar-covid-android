package com.indra.contacttracing.common.di.module

import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.main.di.MainModule
import com.indra.contacttracing.features.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindsMainActivity(): MainActivity

}