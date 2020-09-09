/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.mapper

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.gob.radarcovid.models.domain.ExposureInfo
import org.dpppt.android.sdk.InfectionStatus
import org.dpppt.android.sdk.TracingStatus
import org.dpppt.android.sdk.TracingStatus.ErrorState
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * Example local unit background_shape_exposition_low, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExposureInfoMapperUnitTest {

    private val dataMapper = ExposureInfoDataMapper()

    @Test
    fun testDefaultValues() {
        val exposureInfo = dataMapper.transform(null, null)

        assertNotNull(exposureInfo.level)
        assertNotNull(exposureInfo.lastUpdateTime)
        assertNotNull(exposureInfo.exposureDates)
        assertNotNull(exposureInfo.lastExposureDate)
        assertTrue(exposureInfo.exposureNotificationsEnabled)
    }

    @Test
    fun exposureNotificationsEnabled() {
        val tracingStatus: TracingStatus = mock()


        whenever(tracingStatus.errors).thenReturn(emptyList())
        var exposureInfo = dataMapper.transform(tracingStatus, Date())
        assertEquals(exposureInfo.exposureNotificationsEnabled, true)

        whenever(tracingStatus.errors).thenReturn(null)
        exposureInfo = dataMapper.transform(tracingStatus, Date())
        assertEquals(exposureInfo.exposureNotificationsEnabled, true)

        whenever(tracingStatus.errors)
            .thenReturn(
                arrayListOf(
                    ErrorState.LOCATION_SERVICE_DISABLED,
                    ErrorState.BLE_DISABLED,
                    ErrorState.BLE_NOT_SUPPORTED,
                    ErrorState.BATTERY_OPTIMIZER_ENABLED,
                    ErrorState.SYNC_ERROR_SERVER,
                    ErrorState.SYNC_ERROR_NETWORK,
                    ErrorState.SYNC_ERROR_NO_SPACE,
                    ErrorState.SYNC_ERROR_SSLTLS,
                    ErrorState.SYNC_ERROR_TIMING,
                    ErrorState.SYNC_ERROR_SIGNATURE,
                    ErrorState.SYNC_ERROR_API_EXCEPTION
                )
            )
        exposureInfo = dataMapper.transform(tracingStatus, Date())
        assertEquals(exposureInfo.exposureNotificationsEnabled, true)

        whenever(tracingStatus.errors)
            .thenReturn(arrayListOf(ErrorState.GAEN_UNEXPECTEDLY_DISABLED))
        exposureInfo = dataMapper.transform(tracingStatus, Date())
        assertEquals(exposureInfo.exposureNotificationsEnabled, false)

    }

    @Test
    fun infectionStatusMap() {
        val tracingStatus: TracingStatus = mock()

        whenever(tracingStatus.infectionStatus).thenReturn(InfectionStatus.HEALTHY)
        assertEquals(dataMapper.transform(tracingStatus, mock()).level, ExposureInfo.Level.LOW)

        whenever(tracingStatus.infectionStatus).thenReturn(InfectionStatus.EXPOSED)
        assertEquals(dataMapper.transform(tracingStatus, mock()).level, ExposureInfo.Level.HIGH)

        whenever(tracingStatus.infectionStatus).thenReturn(InfectionStatus.INFECTED)
        assertEquals(dataMapper.transform(tracingStatus, mock()).level, ExposureInfo.Level.INFECTED)
    }

}
