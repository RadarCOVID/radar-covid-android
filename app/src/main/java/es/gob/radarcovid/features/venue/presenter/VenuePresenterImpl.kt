/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venue.presenter

import es.gob.radarcovid.datamanager.usecase.VenueRecordUseCase
import es.gob.radarcovid.features.venue.protocols.VenuePresenter
import es.gob.radarcovid.features.venue.protocols.VenueRouter
import es.gob.radarcovid.features.venue.protocols.VenueView
import javax.inject.Inject

class VenuePresenterImpl @Inject constructor(
    private val router: VenueRouter,
    private val view: VenueView
) : VenuePresenter {

    override fun onRecordButtonClick() {
        router.navigateToVenueRecord()
    }

    override fun onButtonPlacesClick() {
        view.authenticateAndShowPlaces()
    }

    override fun showPlaces() {
        router.navigateToVenuePlaces()
    }

}