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

import android.content.Context
import es.gob.radarcovid.BuildConfig
import org.crowdnotifier.android.sdk.CrowdNotifier
import org.crowdnotifier.android.sdk.model.ExposureEvent
import org.crowdnotifier.android.sdk.model.ProblematicEventInfo
import org.crowdnotifier.android.sdk.model.VenueInfo
import javax.inject.Inject
import javax.inject.Named

class CrowdNotifierRepositoryImpl @Inject constructor(
    @Named("applicationContext") private val context: Context
) : CrowdNotifierRepository {

    override fun getVenueInfo(qrCode: String): VenueInfo =
        CrowdNotifier.getVenueInfo(qrCode, BuildConfig.ENTRY_QR_CODE_PREFIX)

    override fun checkIn(
        arrivalTime: Long,
        departureTime: Long,
        venueInfo: VenueInfo
    ): Long =
        CrowdNotifier.addCheckIn(arrivalTime, departureTime, venueInfo, context)

    override fun checkForMatches(problematicEvents: List<ProblematicEventInfo>): List<ExposureEvent> =
        CrowdNotifier.checkForMatches(
            problematicEvents,
            context
        )

    override fun cleanOldData(days: Int) =
        CrowdNotifier.cleanUpOldData(context, days)
}