package es.gob.covidradar.common.di.module

import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.features.covidreport.form.di.CovidReportModule
import es.gob.covidradar.features.covidreport.form.view.CovidReportActivity
import es.gob.covidradar.features.exposure.di.ExposureModule
import es.gob.covidradar.features.exposure.view.ExposureActivity
import es.gob.covidradar.features.main.di.MainModule
import es.gob.covidradar.features.main.view.MainActivity
import es.gob.covidradar.features.onboarding.di.OnboardingModule
import es.gob.covidradar.features.onboarding.view.OnboardingActivity
import es.gob.covidradar.features.poll.main.di.PollModule
import es.gob.covidradar.features.poll.main.view.PollActivity
import es.gob.covidradar.features.splash.di.SplashModule
import es.gob.covidradar.features.splash.view.SplashActivity
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
    @ContributesAndroidInjector(modules = [ExposureModule::class])
    abstract fun bindsExposureActivity(): ExposureActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CovidReportModule::class])
    abstract fun bindsReportActivity(): CovidReportActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [PollModule::class, PollFragmentsModule::class])
    abstract fun bindsPollActivity(): PollActivity

}