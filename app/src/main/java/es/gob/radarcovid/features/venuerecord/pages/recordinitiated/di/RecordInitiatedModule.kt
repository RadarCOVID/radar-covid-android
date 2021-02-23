/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.recordinitiated.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.presenter.RecordInitiatedPresenterImpl
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.protocols.RecordInitiatedPresenter
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.protocols.RecordInitiatedView
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.view.RecordInitiatedFragment

@Module
class RecordInitiatedModule {

    @Provides
    fun providesView(fragment: RecordInitiatedFragment): RecordInitiatedView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: RecordInitiatedPresenterImpl): RecordInitiatedPresenter = presenter
}