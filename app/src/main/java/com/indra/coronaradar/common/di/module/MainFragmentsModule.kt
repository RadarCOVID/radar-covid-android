package com.indra.coronaradar.common.di.module

import com.indra.coronaradar.common.di.scope.PerFragment
import com.indra.coronaradar.features.health.di.HealthModule
import com.indra.coronaradar.features.health.view.HealthFragment
import com.indra.coronaradar.features.helpline.di.HelplineModule
import com.indra.coronaradar.features.helpline.view.HelplineFragment
import com.indra.coronaradar.features.home.di.HomeModule
import com.indra.coronaradar.features.home.view.HomeFragment
import com.indra.coronaradar.features.mydata.di.MyDataModule
import com.indra.coronaradar.features.mydata.view.MyDataFragment
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