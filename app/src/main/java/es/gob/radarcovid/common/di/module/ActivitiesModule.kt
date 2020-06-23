package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.covidreport.form.di.CovidReportModule
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.di.ExposureModule
import es.gob.radarcovid.features.exposure.view.ExposureActivity
import es.gob.radarcovid.features.main.di.MainModule
import es.gob.radarcovid.features.main.view.MainActivity
import es.gob.radarcovid.features.onboarding.di.OnboardingModule
import es.gob.radarcovid.features.onboarding.view.OnboardingActivity
import es.gob.radarcovid.features.poll.completed.di.PollCompletedModule
import es.gob.radarcovid.features.poll.completed.view.PollCompletedActivity
import es.gob.radarcovid.features.poll.main.di.PollModule
import es.gob.radarcovid.features.poll.main.view.PollActivity
import es.gob.radarcovid.features.splash.di.SplashModule
import es.gob.radarcovid.features.splash.view.SplashActivity

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

    @PerActivity
    @ContributesAndroidInjector(modules = [PollCompletedModule::class])
    abstract fun bindsPollCompletedActivity(): PollCompletedActivity

}