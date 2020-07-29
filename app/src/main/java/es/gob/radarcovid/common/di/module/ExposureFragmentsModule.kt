package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.exposure.region.di.RegionInfoModule
import es.gob.radarcovid.features.exposure.region.view.RegionInfoFragment

@Module
abstract class ExposureFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [RegionInfoModule::class])
    abstract fun bindsRegionInfoFragment(): RegionInfoFragment

}