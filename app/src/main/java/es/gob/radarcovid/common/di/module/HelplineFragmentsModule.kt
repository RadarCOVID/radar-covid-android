package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerSubFragment
import es.gob.radarcovid.features.locale.di.LocaleSelectionModule
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment

@Module
abstract class HelplineFragmentsModule {

    @PerSubFragment
    @ContributesAndroidInjector(modules = [LocaleSelectionModule::class])
    abstract fun bindsLocaleSelectionFragment(): LocaleSelectionFragment

}