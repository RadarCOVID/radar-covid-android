/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.di

import android.content.Context
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.onboarding.presenter.OnboardingPresenterImpl
import es.gob.radarcovid.features.onboarding.protocols.OnboardingPresenter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingRouter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingView
import es.gob.radarcovid.features.onboarding.router.OnboardingRouterImpl
import es.gob.radarcovid.features.onboarding.view.OnboardingActivity
import dagger.Module
import dagger.Provides

@Module
class OnboardingModule {

    @Provides
    fun providesContext(activity: OnboardingActivity): Context = activity

    @Provides
    fun providesView(activity: OnboardingActivity): OnboardingView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: OnboardingPresenterImpl): OnboardingPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: OnboardingRouterImpl): OnboardingRouter = router

}