/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.scanqr.di

import android.content.Context
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.scanqr.presenter.ScanQRPresenterImpl
import es.gob.radarcovid.features.scanqr.protocols.ScanQRPresenter
import es.gob.radarcovid.features.scanqr.protocols.ScanQRView
import es.gob.radarcovid.features.scanqr.view.ScanQRActivity

@Module
class ScanQRModule {

    @Provides
    fun providesContext(activity: ScanQRActivity): Context = activity

    @Provides
    fun providesView(activity: ScanQRActivity): ScanQRView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: ScanQRPresenterImpl): ScanQRPresenter = presenter

}