/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.confirmrecord.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.presenter.ConfirmRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.protocols.ConfirmRecordPresenter
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.protocols.ConfirmRecordView
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.view.ConfirmRecordFragment

@Module
class ConfirmRecordModule {

    @Provides
    fun providesView(fragment: ConfirmRecordFragment): ConfirmRecordView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: ConfirmRecordPresenterImpl): ConfirmRecordPresenter = presenter
}