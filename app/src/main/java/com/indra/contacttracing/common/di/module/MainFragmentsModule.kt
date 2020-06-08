package com.indra.contacttracing.common.di.module

import com.indra.contacttracing.common.di.scope.PerFragment
import com.indra.contacttracing.features.health.di.HealthModule
import com.indra.contacttracing.features.health.view.HealthFragment
import com.indra.contacttracing.features.helpline.di.HelplineModule
import com.indra.contacttracing.features.helpline.view.HelplineFragment
import com.indra.contacttracing.features.home.di.HomeModule
import com.indra.contacttracing.features.home.view.HomeFragment
import com.indra.contacttracing.features.mydata.di.MyDataModule
import com.indra.contacttracing.features.mydata.view.MyDataFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindsHomeFragment(): HomeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [HealthModule::class])
    abstract fun bindsHealthFragment(): HealthFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [MyDataModule::class])
    abstract fun bindsMyDataFragment(): MyDataFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [HelplineModule::class])
    abstract fun bindsHelplineFragment(): HelplineFragment

}