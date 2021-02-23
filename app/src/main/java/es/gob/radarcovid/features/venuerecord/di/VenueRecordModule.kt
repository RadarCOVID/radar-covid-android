/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.di

import android.content.Context
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.onboarding.protocols.OnboardingView
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordPresenter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordRouter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordView
import es.gob.radarcovid.features.venuerecord.router.VenueRecordRouterImpl
import es.gob.radarcovid.features.venuerecord.view.VenueRecordActivity

@Module
class VenueRecordModule {

    @Provides
    fun providesContext(activity: VenueRecordActivity): Context = activity

    @Provides
    fun providesView(activity: VenueRecordActivity): VenueRecordView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: VenueRecordPresenterImpl): VenueRecordPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: VenueRecordRouterImpl): VenueRecordRouter = router
}