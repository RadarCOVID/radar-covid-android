package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.domain.ExposureConfiguration
import es.gob.radarcovid.models.domain.Settings
import es.gob.radarcovid.models.domain.SettingsItem
import es.gob.radarcovid.models.domain.SettingsRiskScore
import es.gob.radarcovid.models.response.ResponseSettings
import es.gob.radarcovid.models.response.ResponseSettingsExposureConfiguration
import es.gob.radarcovid.models.response.ResponseSettingsItem
import es.gob.radarcovid.models.response.ResponseSettingsRiskScore
import javax.inject.Inject

class SettingsDataMapper @Inject constructor() {

    fun transform(responseSettings: ResponseSettings): Settings = with(responseSettings) {
        Settings(
            exposureConfiguration = transform(exposureConfiguration),
            minRiskScore = minRiskScore ?: 0,
            riskScoreClassification = transform(riskScoreClassification)
        )
    }

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
        } ?: emptyList()

    private fun transform(responseSettingsRiskScore: ResponseSettingsRiskScore?): SettingsRiskScore =
        responseSettingsRiskScore?.let {
            SettingsRiskScore(it.label ?: "LOW", it.minValue ?: 0, it.maxValue ?: 0)
        } ?: SettingsRiskScore()
}