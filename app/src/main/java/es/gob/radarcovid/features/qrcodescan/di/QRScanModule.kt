/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.qrcodescan.di

import android.content.Context
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.qrcodescan.presenter.QRScanPresenterImpl
import es.gob.radarcovid.features.qrcodescan.protocols.ScanQRPresenter
import es.gob.radarcovid.features.qrcodescan.protocols.ScanQRView
import es.gob.radarcovid.features.qrcodescan.view.QRScanActivity

@Module
class QRScanModule {

    @Provides
    fun providesContext(activity: QRScanActivity): Context = activity

    @Provides
    fun providesView(activity: QRScanActivity): ScanQRView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: QRScanPresenterImpl): ScanQRPresenter = presenter

}