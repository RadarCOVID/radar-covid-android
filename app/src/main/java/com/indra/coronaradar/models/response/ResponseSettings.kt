package com.indra.coronaradar.models.response

import com.indra.coronaradar.models.response.entity.RiskScoreClassificationEntity

class ResponseSettings : ArrayList<ResponseSettingsItem>()

data class ResponseSettingsItem(
    val minRiskScore: Int,
    val riskScoreClassification: List<RiskScoreClassificationEntity>
)