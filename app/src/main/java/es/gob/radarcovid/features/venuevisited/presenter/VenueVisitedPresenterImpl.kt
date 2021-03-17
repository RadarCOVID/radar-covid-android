/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuevisited.presenter

import es.gob.radarcovid.common.extensions.getDaysAgo
import es.gob.radarcovid.datamanager.repository.EncryptedPreferencesRepository
import es.gob.radarcovid.datamanager.usecase.GetLocaleInfoUseCase
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedPresenter
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedView
import es.gob.radarcovid.models.domain.VenueHeaderItem
import es.gob.radarcovid.models.domain.VenueRecord
import es.gob.radarcovid.models.domain.VenueVisitedItem
import es.gob.radarcovid.models.domain.VenueVisitedRecyclerItem
import javax.inject.Inject

class VenueVisitedPresenterImpl @Inject constructor(
    private val view: VenueVisitedView,
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository,
    private val localeInfoUseCase: GetLocaleInfoUseCase
) : VenueVisitedPresenter {

    override fun viewReady() {
        getVenueList(showHidden = false, reload = false)
    }

    override fun getVenueList(showHidden: Boolean, reload: Boolean) {
        val visitedVenues = encryptedPreferencesRepository.getVisitedVenue()
        if (visitedVenues.isNullOrEmpty()) {
            view.setEmptyList()
        } else {
            val venueList = if (showHidden) {
                visitedVenues.sortedBy { it.dateOut }
            } else {
                visitedVenues.filter { !it.hidden }
                    .sortedBy { it.dateOut }
            }

            val numberHidden =
                visitedVenues.filter { it.hidden }.count()
            val listItems = ArrayList<VenueVisitedRecyclerItem>()
            var daysAgo = -1L
            venueList.forEach {
                val newDaysAgo = it.dateOut!!.getDaysAgo()
                if (daysAgo != newDaysAgo) {
                    daysAgo = newDaysAgo
                    listItems.add(VenueHeaderItem(date = it.dateOut!!))
                }
                listItems.add(VenueVisitedItem(venueItem = it))
            }
            view.setVenueList(
                listItems,
                numberHidden,
                reload,
                localeInfoUseCase.getSelectedLanguageForLocale()
            )
        }
    }

    override fun changeVisibility(venue: VenueRecord, showHidden: Boolean) {
        val venueList = encryptedPreferencesRepository.getVisitedVenue().sortedBy { it.dateOut }
        venueList.find { it.checkOutId == venue.checkOutId }?.hidden = !venue.hidden
        encryptedPreferencesRepository.setVisitedVenue(venueList)
        getVenueList(showHidden, reload = true)
    }

}