/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.checkin.protocols

import es.gob.radarcovid.models.domain.VenueRecord
import java.util.*

interface CheckInView {

    fun performContinueButtonClick()

    fun performCancelButtonClick()

    fun performExitButtonClick()

    fun setVenueInfo(currentVenue: VenueRecord)

    fun startTimer(date: Date)

    fun setVenueData(currentVenue: VenueRecord?)

}

interface CheckInPresenter {

    fun viewReady()

    fun getDateIn(): Date

    fun onContinueButtonClick()

    fun onCancelButtonClick()

    fun onExitButtonClick()
}