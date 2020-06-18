package es.gob.covidradar.models.response

import es.gob.covidradar.models.response.entity.RiskScoreClassificationEntity

class ResponseSettings : ArrayList<ResponseSettingsItem>()

data class ResponseSettingsItem(
    val minRiskScore: Int,
    val riskScoreClassification: List<RiskScoreClassificationEntity>
)