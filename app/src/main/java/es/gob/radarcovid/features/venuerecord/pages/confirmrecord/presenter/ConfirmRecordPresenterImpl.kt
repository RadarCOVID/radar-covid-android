/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.confirmrecord.presenter

import es.gob.radarcovid.datamanager.usecase.VenueRecordUseCase
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.protocols.ConfirmRecordPresenter
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.protocols.ConfirmRecordView
import javax.inject.Inject

class ConfirmRecordPresenterImpl  @Inject constructor(
    private val view: ConfirmRecordView,
    private val venueRecordUseCase: VenueRecordUseCase
) : ConfirmRecordPresenter {

    override fun onContinueButtonClick() {
        view.performContinueButtonClick()
    }

    override fun onCancelButtonClick() {
        view.performCancelButtonClick()
    }
}