package es.gob.radarcovid.common.di.module

import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.health.di.HealthModule
import es.gob.radarcovid.features.health.view.HealthFragment
import es.gob.radarcovid.features.helpline.di.HelplineModule
import es.gob.radarcovid.features.helpline.view.HelplineFragment
import es.gob.radarcovid.features.home.di.HomeModule
import es.gob.radarcovid.features.home.view.HomeFragment
import es.gob.radarcovid.features.mydata.di.MyDataModule
import es.gob.radarcovid.features.mydata.view.MyDataFragment
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