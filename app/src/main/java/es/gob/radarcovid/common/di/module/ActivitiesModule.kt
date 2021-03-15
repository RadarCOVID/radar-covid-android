/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.covidreport.confirmation.ConfirmationActivity
import es.gob.radarcovid.features.covidreport.form.di.CovidReportModule
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.di.ExposureModule
import es.gob.radarcovid.features.exposure.view.ExposureActivity
import es.gob.radarcovid.features.information.di.InformationModule
import es.gob.radarcovid.features.information.view.InformationActivity
import es.gob.radarcovid.features.main.di.MainModule
import es.gob.radarcovid.features.main.view.MainActivity
import es.gob.radarcovid.features.onboarding.di.OnboardingModule
import es.gob.radarcovid.features.onboarding.view.OnboardingActivity
import es.gob.radarcovid.features.qrcodescan.di.QRScanModule
import es.gob.radarcovid.features.qrcodescan.view.QRScanActivity
import es.gob.radarcovid.features.splash.di.SplashModule
import es.gob.radarcovid.features.splash.view.SplashActivity
import es.gob.radarcovid.features.stats.di.StatsModule
import es.gob.radarcovid.features.stats.view.StatsFragment
import es.gob.radarcovid.features.venuerecord.di.VenueRecordModule
import es.gob.radarcovid.features.venuerecord.view.VenueRecordActivity
import es.gob.radarcovid.features.venuevisited.di.VenueVisitedModule
import es.gob.radarcovid.features.venuevisited.view.VenueVisitedActivity

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
    @ContributesAndroidInjector(modules = [ExposureModule::class, ExposureFragmentsModule::class])
    abstract fun bindsExposureActivity(): ExposureActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CovidReportModule::class, CovidReportFragmentModule::class])
    abstract fun bindsReportActivity(): CovidReportActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [InformationModule::class, InformationModule::class])
    abstract fun bindsInformationActivity(): InformationActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [StatsModule::class])
    abstract fun bindsStatsActivity(): StatsFragment

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindsConfirmationActivity(): ConfirmationActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [QRScanModule::class])
    abstract fun bindsQRScanActivity(): QRScanActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [VenueRecordModule::class, VenueRecordFragmentsModule::class])
    abstract fun bindsVenueRecordActivity(): VenueRecordActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [VenueVisitedModule::class])
    abstract fun bindsVenueVisitedActivity(): VenueVisitedActivity

}