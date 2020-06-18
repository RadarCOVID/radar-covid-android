package es.gob.covidradar.common.di.module

import es.gob.covidradar.common.di.scope.PerFragment
import es.gob.covidradar.features.health.di.HealthModule
import es.gob.covidradar.features.health.view.HealthFragment
import es.gob.covidradar.features.helpline.di.HelplineModule
import es.gob.covidradar.features.helpline.view.HelplineFragment
import es.gob.covidradar.features.home.di.HomeModule
import es.gob.covidradar.features.home.view.HomeFragment
import es.gob.covidradar.features.mydata.di.MyDataModule
import es.gob.covidradar.features.mydata.view.MyDataFragment
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