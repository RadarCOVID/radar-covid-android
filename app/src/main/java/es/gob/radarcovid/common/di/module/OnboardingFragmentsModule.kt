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
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.onboarding.pages.legal.di.LegalInfoModule
import es.gob.radarcovid.features.onboarding.pages.legal.view.LegalInfoFragment
import es.gob.radarcovid.features.onboarding.pages.welcome.di.WelcomeModule
import es.gob.radarcovid.features.onboarding.pages.welcome.view.WelcomeFragment

@Module
abstract class OnboardingFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [WelcomeModule::class, WelcomeFragmentsModule::class])
    abstract fun bindsWelcomeFragment(): WelcomeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [LegalInfoModule::class])
    abstract fun bindsLegalInfoFragment(): LegalInfoFragment

}