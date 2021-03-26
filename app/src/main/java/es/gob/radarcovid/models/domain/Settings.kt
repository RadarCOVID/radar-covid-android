/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.models.domain

import es.gob.radarcovid.common.base.Constants.ANALYTICS_PERIOD_DEFAULT
import es.gob.radarcovid.common.base.Constants.NOTIFICATION_REMINDER_DEFAULT

data class Settings(
    val exposureConfiguration: ExposureConfiguration = ExposureConfiguration(),
    val healingTime: HealingTime = HealingTime(),
    val minRiskScore: Int = 1,
    val attenuationThresholdLow: Int = 55,
    val attenuationThresholdMedium: Int = 50,
    val attenuationFactorLow: Float = 1.0f,
    val attenuationFactorMedium: Float = 0.5f,
    val minDurationForExposure: Int = 15,
    val riskScoreClassification: List<SettingsRiskScore> = arrayListOf(
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
    ),
    val appInfo: SettingsAppInfo = SettingsAppInfo(),
    val legalTermsVersion: String = "",
    val radarCovidDownloadUrl: String = "",
    val notificationReminder: Int = NOTIFICATION_REMINDER_DEFAULT,
    val timeBetweenKpi: Int = ANALYTICS_PERIOD_DEFAULT,
    val venueConfiguration: VenueConfiguration = VenueConfiguration()
)

data class ExposureConfiguration(
    val transmission: SettingsItem = SettingsItem(),
    val duration: SettingsItem = SettingsItem(),
    val days: SettingsItem = SettingsItem(),
    val attenuation: SettingsItem = SettingsItem(intArrayOf(1, 1, 1, 1, 1, 1, 1, 1), 100.0f)
)

data class HealingTime(val infectedMinutes: Long = 43200, val exposureHighMinutes: Long = 20160)

data class SettingsItem(
    var value: IntArray = IntArray(8),
    var weight: Float = 0.0f
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SettingsItem

        if (!value.contentEquals(other.value)) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + weight.hashCode()
        return result
    }
}

data class SettingsRiskScore(
    val label: String? = "LOW",
    val minValue: Int? = 0,
    val maxValue: Int? = 0
)

data class SettingsAppInfo(val minVersionName: String = "", val minVersionCode: Int = 1)

data class VenueConfiguration(
    val recordNotification: Int = 60,
    val autoCheckout: Int = 300,
    val troubledPlaceCheck: Int = 120,
    val quarentineAfterExposed: Int = 2880
)