package com.indra.contacttracing.common.di.module

import com.indra.contacttracing.common.di.scope.PerFragment
import com.indra.contacttracing.features.onboarding.pages.legal.di.LegalInfoModule
import com.indra.contacttracing.features.onboarding.pages.legal.view.LegalInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [LegalInfoModule::class])
    abstract fun bindsLegalInfoFragment(): LegalInfoFragment

}