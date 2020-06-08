package com.indra.contacttracing.common.di.module

import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.covidreport.form.di.CovidReportModule
import com.indra.contacttracing.features.covidreport.form.view.CovidReportActivity
import com.indra.contacttracing.features.exposition.di.ExpositionModule
import com.indra.contacttracing.features.exposition.view.ExpositionActivity
import com.indra.contacttracing.features.main.di.MainModule
import com.indra.contacttracing.features.main.view.MainActivity
import com.indra.contacttracing.features.onboarding.di.OnboardingModule
import com.indra.contacttracing.features.onboarding.view.OnboardingActivity
import com.indra.contacttracing.features.splash.di.SplashModule
import com.indra.contacttracing.features.splash.view.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindsSplashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [OnboardingModule::class, OnboardingFragmentsModule::class])
    abstract fun bindsOnboardingActivity(): OnboardingActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentsModule::class])
    abstract fun bindsMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ExpositionModule::class])
    abstract fun bindsExpositionActivity(): ExpositionActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CovidReportModule::class])
    abstract fun bindsReportActivity(): CovidReportActivity

}