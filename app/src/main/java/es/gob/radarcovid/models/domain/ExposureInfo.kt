package es.gob.radarcovid.models.domain

import java.util.*

data class ExposureInfo(
    var level: Level = Level.LOW,
    var lastUpdateTime: Date = Date(),
    var lastExposureDate: Date = Date(),
    var exposureNotificationsEnabled: Boolean = true
) {
    enum class Level { LOW, INFECTED, HIGH }
}