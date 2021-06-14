/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venue.di

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.venue.presenter.VenuePresenterImpl
import es.gob.radarcovid.features.venue.protocols.VenuePresenter
import es.gob.radarcovid.features.venue.protocols.VenueRouter
import es.gob.radarcovid.features.venue.protocols.VenueView
import es.gob.radarcovid.features.venue.router.VenueRouterImpl
import es.gob.radarcovid.features.venue.view.VenueFragment

@Module
class VenueModule {

    @Provides
    fun providesContext(fragment: VenueFragment): Context = fragment.context!!

    @Provides
    fun providesActivity(fragment: VenueFragment): Activity = fragment.activity!!

    @Provides
    fun providesView(fragment: VenueFragment): VenueView = fragment

    @Provides
    fun providesPresenter(presenter: VenuePresenterImpl): VenuePresenter = presenter

    @Provides
    @PerFragment
    fun providesRouter(router: VenueRouterImpl): VenueRouter = router
}