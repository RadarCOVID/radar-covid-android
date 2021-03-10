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

import es.gob.radarcovid.common.extensions.addHours
import es.gob.radarcovid.datamanager.repository.CrowdNotifierRepository
import es.gob.radarcovid.datamanager.repository.EncryptedPreferencesRepository
import es.gob.radarcovid.models.domain.VenueRecord
import org.crowdnotifier.android.sdk.model.ExposureEvent
import org.crowdnotifier.android.sdk.model.ProblematicEventInfo
import java.util.*
import javax.inject.Inject

class VenueMatcherUseCase @Inject constructor(
    private val crowdNotifierRepository: CrowdNotifierRepository,
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository
) {

    fun setVenueExposureInfo(venue: VenueRecord?) =
        encryptedPreferencesRepository.setVenueExposureInfo(venue)

    fun getVenueExposureInfo(): VenueRecord? = encryptedPreferencesRepository.getVenueExposureInfo()

    fun setVenueNotified() {
        val venueList = encryptedPreferencesRepository.getVisitedVenue()
        venueList.forEach { it.isNotified = true }
        encryptedPreferencesRepository.setVisitedVenue(venueList)
    }

    fun checkForMatches(): List<VenueRecord> {
        //Call to backend to get problematics events
        //TODO: falta llamada a backend
        val problematicEvents: List<ProblematicEventInfo> = emptyList()

        val exposures = crowdNotifierRepository.checkForMatches(problematicEvents)
        //val exposures = getExposureEventsMock()

        cleanUpOldData()

        //update local list with exposures
        return if (exposures.isNotEmpty()) {
            val venueList = encryptedPreferencesRepository.getVisitedVenue()
            //val venueList = getVenuesMock()
            exposures.forEach {
                val venue = venueList.find { x -> x.checkOutId == it.id }
                if (venue != null) {
                    venue.isExposed = true
                }
            }
            encryptedPreferencesRepository.setVisitedVenue(venueList)

            venueList.filter { it.isExposed }.sortedBy { it.dateOut }.toList()
        } else {
            emptyList()
        }

    }

    private fun cleanUpOldData() {
        crowdNotifierRepository.cleanOldData(14)
        encryptedPreferencesRepository.cleanVisitedVenue(14)
    }

    private fun getExposureEventsMock(): List<ExposureEvent> {
        val exposure1 = ExposureEvent(1, Date().addHours(-1).time, Date().time, "Exposure Event")
        val exposure2 =
            ExposureEvent(3, Date().addHours(-3).time, Date().addHours(-2).time, "Exposure Event")
        return arrayOf(exposure1, exposure2).toList()
    }

    private fun getVenuesMock(): List<VenueRecord> {
        val venue1 = VenueRecord(
            qr = "",
            dateIn = Date().addHours(-1),
            dateOut = Date(),
            checkOutId = 1,
            name = "Venta Pepe"
        )

        val venue2 = VenueRecord(
            qr = "",
            dateIn = Date().addHours(-2),
            dateOut = Date().addHours(-1),
            checkOutId = 2,
            name = "Venta Pepe"
        )

        val venue3 = VenueRecord(
            qr = "",
            dateIn = Date().addHours(-3),
            dateOut = Date().addHours(-2),
            checkOutId = 3,
            name = "Venta Pepe"
        )
        return arrayOf(venue1, venue2, venue3).toList()
    }

}