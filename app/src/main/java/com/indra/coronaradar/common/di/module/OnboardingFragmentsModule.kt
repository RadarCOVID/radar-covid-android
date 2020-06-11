package com.indra.coronaradar.common.di.module

import com.indra.coronaradar.common.di.scope.PerFragment
import com.indra.coronaradar.features.onboarding.pages.legal.di.LegalInfoModule
import com.indra.coronaradar.features.onboarding.pages.legal.view.LegalInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [LegalInfoModule::class])
    abstract fun bindsLegalInfoFragment(): LegalInfoFragment

}