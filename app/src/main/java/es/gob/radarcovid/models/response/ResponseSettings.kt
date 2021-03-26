/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.models.response

import es.gob.radarcovid.common.base.Constants.ANALYTICS_PERIOD_DEFAULT
import es.gob.radarcovid.common.base.Constants.NOTIFICATION_REMINDER_DEFAULT

data class ResponseSettings(
    val exposureConfiguration: ResponseSettingsExposureConfiguration? = ResponseSettingsExposureConfiguration(),
    val timeBetweenStates: ResponseSettingsTimeBetweenStates? = ResponseSettingsTimeBetweenStates(),
    val minRiskScore: Int? = 0,
    val minDurationForExposure: Int? = 15,
    val riskScoreClassification: List<ResponseSettingsRiskScore>? = emptyList(),
    val attenuationDurationThresholds: ResponseSettingsAttenuationThresholds? = ResponseSettingsAttenuationThresholds(),
    val attenuationFactor: ResponseSettingsAttenuationFactor? = ResponseSettingsAttenuationFactor(),
    val applicationVersion: ResponseSettingsAppVersion? = ResponseSettingsAppVersion(),
    val legalTermsVersion: String? = "",
    val radarCovidDownloadUrl: String? = "",
    val notificationReminder: Int? = NOTIFICATION_REMINDER_DEFAULT,
    val timeBetweenKpi: Int? = ANALYTICS_PERIOD_DEFAULT,
    val venueConfiguration: ResponseVenueConfiguration? = ResponseVenueConfiguration()
)

data class ResponseSettingsExposureConfiguration(
    val transmission: ResponseSettingsItem? = ResponseSettingsItem(),
    val duration: ResponseSettingsItem? = ResponseSettingsItem(),
    val days: ResponseSettingsItem? = ResponseSettingsItem(),
    val attenuation: ResponseSettingsItem? = ResponseSettingsItem()
)

data class ResponseSettingsTimeBetweenStates(
    val highRiskToLowRisk: Long? = 20160,
    val infectedToHealthy: Long? = 43200
)

data class ResponseSettingsItem(
    val riskLevelValue1: Int? = 0,
    val riskLevelValue2: Int? = 0,
    val riskLevelValue3: Int? = 0,
    val riskLevelValue4: Int? = 0,
    val riskLevelValue5: Int? = 0,
    val riskLevelValue6: Int? = 0,
    val riskLevelValue7: Int? = 0,
    val riskLevelValue8: Int? = 0,
    val riskLevelWeight: Float? = 0f
)

data class ResponseSettingsRiskScore(
    val label: String? = "LOW",
    val minValue: Int? = 0,
    val maxValue: Int? = 0
)

data class ResponseSettingsAttenuationThresholds(val low: Int? = 50, val medium: Int? = 55)

data class ResponseSettingsAttenuationFactor(val low: Float? = 0f, val medium: Float? = 0f)

data class ResponseSettingsAppVersion(val android: ResponseSettingsAppVersionItem? = ResponseSettingsAppVersionItem())

data class ResponseSettingsAppVersionItem(val version: String? = "1.0", val compilation: Int? = 1)

data class ResponseVenueConfiguration(
    val recordNotification: Int = 60,
    val autoCheckout: Int = 300,
    val troubledPlaceCheck: Int = 120,
    val quarentineAfterExposed: Int = 2880
)