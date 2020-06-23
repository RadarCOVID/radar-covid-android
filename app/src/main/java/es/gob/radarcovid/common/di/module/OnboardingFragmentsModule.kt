package es.gob.radarcovid.common.di.module

import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.onboarding.pages.legal.di.LegalInfoModule
import es.gob.radarcovid.features.onboarding.pages.legal.view.LegalInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [LegalInfoModule::class])
    abstract fun bindsLegalInfoFragment(): LegalInfoFragment

}