package es.gob.covidradar.common.di.module

import es.gob.covidradar.common.di.scope.PerFragment
import es.gob.covidradar.features.onboarding.pages.legal.di.LegalInfoModule
import es.gob.covidradar.features.onboarding.pages.legal.view.LegalInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [LegalInfoModule::class])
    abstract fun bindsLegalInfoFragment(): LegalInfoFragment

}