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

import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.CrowdNotifierRepository
import es.gob.radarcovid.datamanager.repository.EncryptedPreferencesRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.ProblematicEventOuterClass
import es.gob.radarcovid.models.domain.VenueRecord
import okhttp3.ResponseBody
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

    private fun setVenueExposureInfo(venue: VenueRecord?) =
        encryptedPreferencesRepository.setVenueExposureInfo(venue)

    fun getVenueExposureInfo(): VenueRecord? = encryptedPreferencesRepository.getVenueExposureInfo()

    private fun getVisitedVenueList(): List<VenueRecord> =
        encryptedPreferencesRepository.getVisitedVenue()

    fun setVenueNotified() {
        val venueList = getVisitedVenueList()
        venueList.forEach { it.isNotified = true }
        encryptedPreferencesRepository.setVisitedVenue(venueList)
    }

    fun checkForMatches(): List<VenueRecord> {
        //Call to backend to get problematics events
        val problematicEvents = getTraceKeys(preferencesRepository.getLastKeyBundleTag())

        //Check for matches
        val exposures = crowdNotifierRepository.checkForMatches(problematicEvents)

        cleanUpOldData()

        if (exposures.isNotEmpty()) {
            //update local list with exposures
            val venueList = getVisitedVenueList()
            exposures.forEach {
                val venue = venueList.find { x -> x.checkOutId == it.id }
                if (venue != null) {
                    venue.isExposed = true
                }
            }
            encryptedPreferencesRepository.setVisitedVenue(venueList)

            //Set actual exposure
            val exposedList = venueList.filter { it.isExposed }.sortedBy { it.dateOut }.toList()
            setVenueExposureInfo(exposedList.last())

            return exposedList

        } else {
            //Check actual exposure
            val exposureVenue = getVisitedVenueList().firstOrNull { it.isExposed }
            if (exposureVenue == null) {
                setVenueExposureInfo(null)
            }

            return emptyList()
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
}