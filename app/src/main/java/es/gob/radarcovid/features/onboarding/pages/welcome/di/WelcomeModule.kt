/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.welcome.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.onboarding.pages.welcome.presenter.WelcomePresenterImpl
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomePresenter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeRouter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeView
import es.gob.radarcovid.features.onboarding.pages.welcome.router.WelcomeRouterImpl
import es.gob.radarcovid.features.onboarding.pages.welcome.view.WelcomeFragment

@Module
class WelcomeModule {

    @Provides
    fun providesView(fragment: WelcomeFragment): WelcomeView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: WelcomePresenterImpl): WelcomePresenter = presenter

    @Provides
    @PerFragment
    fun providesRouter(router: WelcomeRouterImpl): WelcomeRouter = router

}