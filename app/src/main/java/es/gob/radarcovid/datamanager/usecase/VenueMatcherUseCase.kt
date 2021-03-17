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

import es.gob.radarcovid.common.extensions.addDays
import es.gob.radarcovid.common.extensions.addHours
import es.gob.radarcovid.common.extensions.addMinutes
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.CrowdNotifierRepository
import es.gob.radarcovid.datamanager.repository.EncryptedPreferencesRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.ProblematicEventOuterClass
import es.gob.radarcovid.models.domain.VenueRecord
import okhttp3.ResponseBody
import org.crowdnotifier.android.sdk.model.ExposureEvent
import org.crowdnotifier.android.sdk.model.ProblematicEventInfo
import retrofit2.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject

class VenueMatcherUseCase @Inject constructor(
    private val crowdNotifierRepository: CrowdNotifierRepository,
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository,
    private val preferencesRepository: PreferencesRepository,
    private val apiRepository: ApiRepository
) {

    companion object {
        private const val KEY_BUNDLE_TAG_HEADER = "x-key-bundle-tag"
    }

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
        val problematicEvents = getTraceKeys(preferencesRepository.getLastKeyBundleTag())

        //Check for matches
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
        crowdNotifierRepository.cleanOldData(preferencesRepository.getQuarantineAfterVenueExposedTime())
        encryptedPreferencesRepository.cleanVisitedVenue(preferencesRepository.getQuarantineAfterVenueExposedTime())
    }

    private fun getTraceKeys(keyBundleTag: Long): List<ProblematicEventInfo> {
        try {
            val response = apiRepository.getTraceKeys(keyBundleTag)
            if (response.isSuccessful) {
                return handleSuccessfulResponse(response)
            }
        } catch (e: IOException) {
            return emptyList()
        }
        return emptyList()
    }

    private fun handleSuccessfulResponse(response: Response<ResponseBody>): List<ProblematicEventInfo> {
        return try {
            val keyBundleTag =
                response.headers()[KEY_BUNDLE_TAG_HEADER]!!
                    .toLong()
            preferencesRepository.setLastKeyBundleTag(keyBundleTag)
            val problematicEventWrapper: ProblematicEventOuterClass.ProblematicEventWrapper =
                ProblematicEventOuterClass.ProblematicEventWrapper.parseFrom(
                    response.body()!!.byteStream()
                )
            val problematicEventInfos = ArrayList<ProblematicEventInfo>()
            for (event in problematicEventWrapper.eventsList) {
                problematicEventInfos.add(
                    ProblematicEventInfo(
                        event.identity.toByteArray(),
                        event.secretKeyForIdentity.toByteArray(),
                        event.startTime,
                        event.endTime,
                        event.message.toByteArray(),
                        event.nonce.toByteArray()
                    )
                )
            }
            problematicEventInfos
        } catch (e: IOException) {
            return emptyList()
        }
    }

    private fun getExposureEventsMock(): List<ExposureEvent> {
        val exposure1 = ExposureEvent(1, Date().addHours(-1).time, Date().time, "Exposure Event")
        val exposure2 =
            ExposureEvent(3, Date().addHours(-3).time, Date().addHours(-2).time, "Exposure Event")
        return arrayOf(exposure1, exposure2).toList()
    }

    fun getVenuesMock(): List<VenueRecord> {
        val venue1 = VenueRecord(
            qr = "",
            dateIn = Date().addDays(-1).addHours(-1),
            dateOut = Date().addDays(-1),
            checkOutId = 1,
            name = "Venta Pepe"
        )

        val venue2 = VenueRecord(
            qr = "",
            dateIn = Date().addHours(-1),
            dateOut = Date().addMinutes(-30),
            checkOutId = 2,
            name = "Venta Pepe"
        )

        val venue3 = VenueRecord(
            qr = "",
            dateIn = Date().addHours(-3),
            dateOut = Date().addHours(-1),
            checkOutId = 3,
            name = "Venta Pepe"
        )
        val venue4 = VenueRecord(
            qr = "",
            dateIn = Date().addHours(-5),
            dateOut = Date().addHours(-2),
            checkOutId = 4,
            hidden = true,
            name = "Bar el camino"
        )
        return arrayOf(venue1, venue2, venue3, venue4).toList()
    }

}