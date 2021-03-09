/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.CrowdNotifierRepository
import es.gob.radarcovid.datamanager.repository.EncryptedPreferencesRepository
import org.crowdnotifier.android.sdk.model.ExposureEvent
import org.crowdnotifier.android.sdk.model.ProblematicEventInfo
import javax.inject.Inject

class VenueMatcherUseCase @Inject constructor(
    private val crowdNotifierRepository: CrowdNotifierRepository,
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository
) {

    fun checkForMatches(): List<ExposureEvent> {
        //Call to backend to get problematics events
        //TODO: falta llamada a backend
        val problematicEvents: List<ProblematicEventInfo> = emptyList()

        val exposures = crowdNotifierRepository.checkForMatches(problematicEvents)

        cleanUpOldData()

        //update local list
        val venueList = encryptedPreferencesRepository.getVisitedVenue()
        exposures.forEach {

        }

        return exposures

    }

    private fun cleanUpOldData() {
        crowdNotifierRepository.cleanOldData(14)
        //TODO: Hace falta borrar los stios de local??
//        DiaryStorage.getInstance(context)
//            .removeEntriesBefore(VenueMatcherWorker.DAYS_TO_KEEP_VENUE_VISITS)
    }

}