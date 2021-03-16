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
import es.gob.radarcovid.datamanager.usecase.VenueMatcherUseCase
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedPresenter
import es.gob.radarcovid.features.venuevisited.protocols.VenueVisitedView
import es.gob.radarcovid.models.domain.VenueHeaderItem
import es.gob.radarcovid.models.domain.VenueVisitedItem
import es.gob.radarcovid.models.domain.VenueVisitedRecyclerItem
import javax.inject.Inject

class VenueVisitedPresenterImpl @Inject constructor(
    private val view: VenueVisitedView,
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository,
    private val venueMatcherUseCase: VenueMatcherUseCase,
    private val localeInfoUseCase: GetLocaleInfoUseCase
) : VenueVisitedPresenter {

    override fun viewReady() {
        getVenueList(false)
    }

    override fun getVenueList(showHidden: Boolean) {
        //val venueList = encryptedPreferencesRepository.getVisitedVenue().sortedBy { it.dateOut }
        val venueList = if (showHidden) {
            venueMatcherUseCase.getVenuesMock().sortedBy { it.dateOut }
            //encryptedPreferencesRepository.getVisitedVenue().sortedBy { it.dateOut }
        } else {
            venueMatcherUseCase.getVenuesMock().filter { !it.hidden }.sortedBy { it.dateOut }
            //encryptedPreferencesRepository.getVisitedVenue().filter { it.hidden }.sortedBy { it.dateOut }
        }
        if (!venueList.isNullOrEmpty()) {
            //val numberHidden = encryptedPreferencesRepository.getVisitedVenue().filter { it.hidden }.count()
            val numberHidden = venueMatcherUseCase.getVenuesMock().filter { it.hidden }.count()
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
                localeInfoUseCase.getSelectedLanguageForLocale()
            )
        }
    }

}