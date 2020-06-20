package es.gob.covidradar.models.response

data class ResponseSettings(
    val exposureConfiguration: ResponseSettingsExposureConfiguration? = ResponseSettingsExposureConfiguration(),
    val minRiskScore: Int? = 0,
    val riskScoreClassification: List<ResponseSettingsRiskScore>? = emptyList()
)

data class ResponseSettingsExposureConfiguration(
    val transmission: ResponseSettingsItem? = ResponseSettingsItem(),
    val duration: ResponseSettingsItem? = ResponseSettingsItem(),
    val days: ResponseSettingsItem? = ResponseSettingsItem(),
    val attenuation: ResponseSettingsItem? = ResponseSettingsItem()
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