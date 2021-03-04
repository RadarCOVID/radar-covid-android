/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.checkout.presenter

import es.gob.radarcovid.common.extensions.addHours
import es.gob.radarcovid.common.extensions.addMinutes
import es.gob.radarcovid.datamanager.usecase.VenueRecordUseCase
import es.gob.radarcovid.features.venuerecord.pages.checkout.protocols.CheckOutPresenter
import es.gob.radarcovid.features.venuerecord.pages.checkout.protocols.CheckOutView
import es.gob.radarcovid.models.domain.VenueRecord
import javax.inject.Inject

enum class VenueTimeOut { NOW, OPT_30, OPT_1, OPT_2, OPT_4, OPT_5 }

class CheckOutPresenterImpl @Inject constructor(
    private val view: CheckOutView,
    private val venueRecordUseCase: VenueRecordUseCase
) : CheckOutPresenter {

    private var currentVenue: VenueRecord? = null

    override fun viewReady() {
        currentVenue = venueRecordUseCase.getCurrentVenue()
        view.setVenueData(currentVenue)
    }

    override fun onContinueButtonClick() {
        view.performContinueButtonClick()
    }

    override fun onCancelButtonClick() {
        view.performCancelButtonClick()
    }

    override fun onCloseButtonClick() {
        view.performCloseButtonClick()
    }

    override fun onControlTimeClick() {
        view.setButtonContinueEnabled(true)
    }
}