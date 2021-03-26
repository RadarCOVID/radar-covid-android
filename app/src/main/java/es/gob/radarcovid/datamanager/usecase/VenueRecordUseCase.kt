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
import es.gob.radarcovid.models.domain.VenueRecord
import io.reactivex.rxjava3.core.Completable
import org.crowdnotifier.android.sdk.model.VenueInfo
import java.util.*
import javax.inject.Inject

class VenueRecordUseCase @Inject constructor(
    private val crowdNotifierRepository: CrowdNotifierRepository,
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository
) {

    fun isCurrentVenue(): Boolean =
        encryptedPreferencesRepository.getCurrentVenue() != null

    fun getCurrentVenue(): VenueRecord? =
        encryptedPreferencesRepository.getCurrentVenue()

    private fun setCurrentVenue(currentVenue: VenueRecord) =
        encryptedPreferencesRepository.setCurrentVenueRecord(currentVenue)

    fun getVenueInfo(qrCode: String): VenueInfo =
        crowdNotifierRepository.getVenueInfo(qrCode)

    fun checkIn(qrCaptured: String): Completable =
        Completable.create { emitter ->
            try {
                val venueInfo = getVenueInfo(qrCaptured)
                val currentVenueRecord = VenueRecord(
                    qr = qrCaptured,
                    name = venueInfo.name,
                    dateIn = Date()
                )
                setCurrentVenue(currentVenueRecord)

                emitter.onComplete()

            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

    fun checkOut(dateOut: Date): Completable =
        Completable.create { emitter ->
            try {
                val currentVenue = encryptedPreferencesRepository.getCurrentVenue()
                if (currentVenue != null) {
                    val listVisited =
                        encryptedPreferencesRepository.getVisitedVenue().toMutableList()
                    val venueInfo = getVenueInfo(currentVenue.qr)

                    currentVenue.dateOut = dateOut
                    currentVenue.checkOutId = crowdNotifierRepository.checkIn(
                        currentVenue.dateIn.time,
                        currentVenue.dateOut!!.time,
                        venueInfo
                    )
                    listVisited.add(currentVenue)

                    encryptedPreferencesRepository.setVisitedVenue(listVisited)
                    encryptedPreferencesRepository.removeCurrentVenue()
                }

                emitter.onComplete()

            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

    fun cancelCheckIn() {
        encryptedPreferencesRepository.removeCurrentVenue()
    }

}