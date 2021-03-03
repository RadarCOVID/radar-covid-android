/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.checkout.protocols

import es.gob.radarcovid.features.venuerecord.pages.checkout.presenter.VenueTimeOut
import es.gob.radarcovid.models.domain.VenueRecord

interface CheckOutView {

    fun performContinueButtonClick()

    fun performCancelButtonClick()

    fun performCloseButtonClick()

    fun setVenueData(currentVenue: VenueRecord?)

    fun getTimeOut(): VenueTimeOut

    fun setButtonContinueEnabled(enabled: Boolean)
}

interface CheckOutPresenter {

    fun viewReady()

    fun onContinueButtonClick()

    fun onCancelButtonClick()

    fun onCloseButtonClick()

    fun onControlTimeClick()
}