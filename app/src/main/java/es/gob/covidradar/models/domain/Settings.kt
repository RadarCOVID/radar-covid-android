package es.gob.covidradar.models.domain

data class Settings(
    val exposureConfiguration: ExposureConfiguration = ExposureConfiguration(),
    val minRiskScore: Int = 0,
    val riskScoreClassification: List<SettingsRiskScore> = emptyList()
)

data class ExposureConfiguration(
    val transmission: SettingsItem = SettingsItem(),
    val duration: SettingsItem = SettingsItem(),
    val days: SettingsItem = SettingsItem(),
    val attenuation: SettingsItem = SettingsItem()
)

data class SettingsItem(
    var riskLevelValue: IntArray = IntArray(8),
    var riskLevelWeight: Float = 0.0f
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SettingsItem

        if (!riskLevelValue.contentEquals(other.riskLevelValue)) return false
        if (riskLevelWeight != other.riskLevelWeight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = riskLevelValue.contentHashCode()
        result = 31 * result + riskLevelWeight.hashCode()
        return result
    }
}

data class SettingsRiskScore(
    val label: String? = "LOW",
    val minValue: Int? = 0,
    val maxValue: Int? = 0
)