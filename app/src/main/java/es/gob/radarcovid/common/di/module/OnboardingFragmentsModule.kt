package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.common.di.scope.PerSubFragment
import es.gob.radarcovid.features.locale.di.LocaleSelectionModule
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment
import es.gob.radarcovid.features.onboarding.pages.legal.di.LegalInfoModule
import es.gob.radarcovid.features.onboarding.pages.legal.view.LegalInfoFragment

@Module
abstract class OnboardingFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [LegalInfoModule::class])
    abstract fun bindsLegalInfoFragment(): LegalInfoFragment

    @PerSubFragment
    @ContributesAndroidInjector(modules = [LocaleSelectionModule::class])
    abstract fun bindsLocaleSelectionFragment(): LocaleSelectionFragment

}