package com.indra.coronaradar.common.di.module

import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.features.covidreport.form.di.CovidReportModule
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
import com.indra.coronaradar.features.exposure.di.ExpositionModule
import com.indra.coronaradar.features.exposure.view.ExpositionActivity
import com.indra.coronaradar.features.main.di.MainModule
import com.indra.coronaradar.features.main.view.MainActivity
import com.indra.coronaradar.features.onboarding.di.OnboardingModule
import com.indra.coronaradar.features.onboarding.view.OnboardingActivity
import com.indra.coronaradar.features.splash.di.SplashModule
import com.indra.coronaradar.features.splash.view.SplashActivity
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