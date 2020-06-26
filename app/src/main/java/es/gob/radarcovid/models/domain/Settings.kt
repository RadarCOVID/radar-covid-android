package es.gob.radarcovid.models.domain

data class Settings(
    val exposureConfiguration: ExposureConfiguration = ExposureConfiguration(),
    val minRiskScore: Int = 0,
    val attenuationThresholdLow: Int = 0,
    val attenuationThresholdMedium: Int = 0,
    val riskScoreClassification: List<SettingsRiskScore> = emptyList()
)

data class ExposureConfiguration(
    val transmission: SettingsItem = SettingsItem(),
    val duration: SettingsItem = SettingsItem(),
    val days: SettingsItem = SettingsItem(),
    val attenuation: SettingsItem = SettingsItem()
)

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