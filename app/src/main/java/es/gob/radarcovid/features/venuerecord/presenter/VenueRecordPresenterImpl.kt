/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.presenter

import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordPresenter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordRouter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordView
import javax.inject.Inject

class VenueRecordPresenterImpl  @Inject constructor(
    private val view: VenueRecordView,
    private val router: VenueRecordRouter
) : VenueRecordPresenter {

    companion object {
        const val ERROR_CAPTURED_CODE_FRAGMENT = 0
        const val CAPTURED_CODE_FRAGMENT = 1
        const val INITIATED_RECORD_FRAGMENT = 2
    }

    override fun startScan() {
        view.startQRScan()
    }

    override fun onOkScan(data: String) {
        view.showFragment(CAPTURED_CODE_FRAGMENT)
    }

    override fun onErrorScan() {
        view.showFragment(ERROR_CAPTURED_CODE_FRAGMENT)
    }

    override fun doCheckIn() {
        view.showFragment(INITIATED_RECORD_FRAGMENT)
    }
}