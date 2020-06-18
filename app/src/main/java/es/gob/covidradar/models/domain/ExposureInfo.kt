package es.gob.covidradar.models.domain

import java.util.*

data class ExposureInfo(var level: Level = Level.LOW, var lastUpdateTime: Date = Date()) {
    enum class Level { LOW, MEDIUM, HIGH }
}