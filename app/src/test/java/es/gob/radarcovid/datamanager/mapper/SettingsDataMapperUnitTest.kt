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
import es.gob.radarcovid.models.domain.SettingsAppInfo
import es.gob.radarcovid.models.domain.SettingsItem
import es.gob.radarcovid.models.domain.SettingsRiskScore
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class SettingsDataMapperUnitTest {

    private val settingsDataMapper: SettingsDataMapper = SettingsDataMapper()

    @Test
    fun testDefaultValues() {
        val settings = settingsDataMapper.transform(null)
        assertNotNull(settings)

        //EXPOSURE CONFIGURATION
        assertEquals(
            settings.exposureConfiguration.transmission,
            SettingsItem(intArrayOf(0, 0, 0, 0, 0, 0, 0, 0), 0.0f)
        )
        assertEquals(
            settings.exposureConfiguration.duration,
            SettingsItem(intArrayOf(0, 0, 0, 0, 0, 0, 0, 0), 0.0f)
        )
        assertEquals(
            settings.exposureConfiguration.days,
            SettingsItem(intArrayOf(0, 0, 0, 0, 0, 0, 0, 0), 0.0f)
        )
        assertEquals(
            settings.exposureConfiguration.attenuation,
            SettingsItem(intArrayOf(1, 1, 1, 1, 1, 1, 1, 1), 100.0f)
        )

        //HEALING TIME
        assertEquals(settings.healingTime.exposureHighMinutes, 20160)
        assertEquals(settings.healingTime.infectedMinutes, 43200)

        assertEquals(settings.minRiskScore, 1)

        assertEquals(settings.attenuationThresholdLow, 55)
        assertEquals(settings.attenuationThresholdMedium, 50)

        assertEquals(settings.attenuationFactorLow, 1.0f)
        assertEquals(settings.attenuationFactorMedium, 0.5f)

        assertEquals(settings.minDurationForExposure, 15)

        assertEquals(
            settings.riskScoreClassification, arrayListOf(
                SettingsRiskScore(
                    "LOW",
                    0,
                    4095
                ),
                SettingsRiskScore(
                    "HIGH",
                    4096,
                    4096
                )
            )
        )

        assertEquals(settings.appInfo, SettingsAppInfo("", 1))
    }

    @Test
    fun testSettingsDataMapper() {
        assertNotNull(settingsDataMapper.transform(mock()))
    }

}