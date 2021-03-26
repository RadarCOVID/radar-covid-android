/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.checkin.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.venuerecord.pages.checkin.presenter.CheckInPresenterImpl
import es.gob.radarcovid.features.venuerecord.pages.checkin.protocols.CheckInPresenter
import es.gob.radarcovid.features.venuerecord.pages.checkin.protocols.CheckInView
import es.gob.radarcovid.features.venuerecord.pages.checkin.view.CheckInFragment

@Module
class CheckInModule {

    @Provides
    fun providesView(fragment: CheckInFragment): CheckInView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: CheckInPresenterImpl): CheckInPresenter = presenter
}