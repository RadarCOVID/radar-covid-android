/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuevisited.di

import android.content.Context
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.features.venuevisited.presenter.VenueVisitedPresenterImpl
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedPresenter
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedView
import es.gob.radarcovid.features.venuevisited.view.VenueVisitedActivity

@Module
class VenueVisitedModule {

    @Provides
    fun providesContext(activity: VenueVisitedActivity): Context = activity

    @Provides
    fun providesView(activity: VenueVisitedActivity): VenueVisitedView = activity

    @Provides
    fun providesPresenter(presenter: VenueVisitedPresenterImpl): VenueVisitedPresenter = presenter

}