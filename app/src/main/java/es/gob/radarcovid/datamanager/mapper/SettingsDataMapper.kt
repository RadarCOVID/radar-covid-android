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

import es.gob.radarcovid.common.base.Constants.ANALYTICS_PERIOD_DEFAULT
import es.gob.radarcovid.common.base.Constants.NOTIFICATION_REMINDER_DEFAULT
import es.gob.radarcovid.models.domain.*
import es.gob.radarcovid.models.response.*
import javax.inject.Inject

class SettingsDataMapper @Inject constructor() {

    fun transform(responseSettings: ResponseSettings?): Settings = responseSettings?.let {
        with(responseSettings) {
            Settings(
                exposureConfiguration = transform(exposureConfiguration),
                healingTime = transform(timeBetweenStates),
                minRiskScore = minRiskScore ?: 1,
                attenuationThresholdLow = attenuationDurationThresholds?.low ?: 50,
                attenuationThresholdMedium = attenuationDurationThresholds?.medium ?: 55,
                attenuationFactorLow = attenuationFactor?.low ?: 1.0f,
                attenuationFactorMedium = attenuationFactor?.medium ?: 0.5f,
                minDurationForExposure = minDurationForExposure ?: 15,
                riskScoreClassification = transform(riskScoreClassification),
                appInfo = transform(applicationVersion),
                legalTermsVersion = legalTermsVersion ?: "",
                radarCovidDownloadUrl = radarCovidDownloadUrl ?: "",
                notificationReminder = notificationReminder ?: NOTIFICATION_REMINDER_DEFAULT,
                timeBetweenKpi = timeBetweenKpi ?: ANALYTICS_PERIOD_DEFAULT
            )
        }
    } ?: Settings()

    private fun transform(responseSettingsItem: ResponseSettingsItem?): SettingsItem =
        responseSettingsItem?.let {
            SettingsItem(
                intArrayOf(
                    it.riskLevelValue1 ?: 1,
                    it.riskLevelValue2 ?: 1,
                    it.riskLevelValue3 ?: 1,
                    it.riskLevelValue4 ?: 1,
                    it.riskLevelValue5 ?: 1,
                    it.riskLevelValue6 ?: 1,
                    it.riskLevelValue7 ?: 1,
                    it.riskLevelValue8 ?: 1
                ),
                it.riskLevelWeight ?: 0.0f
            )
        } ?: SettingsItem()

    private fun transform(responseSettingsTimeBetweenStates: ResponseSettingsTimeBetweenStates?): HealingTime =
        responseSettingsTimeBetweenStates?.let {
            HealingTime(it.infectedToHealthy ?: 43200, it.highRiskToLowRisk ?: 20160)
        } ?: HealingTime()

    private fun transform(exposureConfiguration: ResponseSettingsExposureConfiguration?): ExposureConfiguration =
        exposureConfiguration?.let {
            ExposureConfiguration(
                transmission = transform(it.transmission),
                duration = transform(it.duration),
                days = transform(it.days),
                attenuation = transform(it.attenuation)
            )
        } ?: ExposureConfiguration()

    private fun transform(riskScoreClassification: List<ResponseSettingsRiskScore>?): List<SettingsRiskScore> =
        riskScoreClassification?.let {
            it.map { riskScore -> transform(riskScore) }
        } ?: arrayListOf(
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

    private fun transform(responseSettingsRiskScore: ResponseSettingsRiskScore?): SettingsRiskScore =
        responseSettingsRiskScore?.let {
            SettingsRiskScore(it.label ?: "LOW", it.minValue ?: 0, it.maxValue ?: 0)
        } ?: SettingsRiskScore()

    private fun transform(responseSettingsAppVersion: ResponseSettingsAppVersion?): SettingsAppInfo =
        responseSettingsAppVersion?.let {
            SettingsAppInfo(it.android?.version ?: "1.0", it.android?.compilation ?: 1)
        } ?: SettingsAppInfo()
}