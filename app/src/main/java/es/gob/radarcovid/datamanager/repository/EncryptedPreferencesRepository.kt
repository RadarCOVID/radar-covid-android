/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.VenueRecord

interface EncryptedPreferencesRepository {

    fun getCurrentVenue(): VenueRecord?

    fun setCurrentVenueRecord(current: VenueRecord)

    fun removeCurrentVenue()

    fun getVisitedVenue(): List<VenueRecord>

    fun setVisitedVenue(venues: List<VenueRecord>)

    fun cleanVisitedVenue(maxMinutesToKeep: Int)

    fun setVenueExposureInfo(venue: VenueRecord?)

    fun getVenueExposureInfo(): VenueRecord?
}