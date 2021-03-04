/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.protocols

import es.gob.radarcovid.common.view.RequestView
import es.gob.radarcovid.features.venuerecord.pages.checkout.presenter.VenueTimeOut

interface VenueRecordView: RequestView {

    fun startQRScan()

    fun showFragment(position: Int)

    fun onBackButtonClick()

    fun exit()

    fun startVenueRecordWorker()

    fun cancelVenueRecordWorker()

}

interface VenueRecordPresenter {

    fun viewReady()

    fun getCurrentVenueName(): String

    fun onContinueButtonClick(pageIndex: Int)

    fun onOkScan(data: String)

    fun onErrorScan()

    fun cancelRecord()

    fun onBackPressed(pageIndex: Int)

    fun setVenueTimeOut(timeOut: VenueTimeOut)
}

interface VenueRecordRouter {

    //fun openQRCodeScan()
}