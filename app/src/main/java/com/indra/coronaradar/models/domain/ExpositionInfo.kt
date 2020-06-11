package com.indra.coronaradar.models.domain

import java.util.*

data class ExpositionInfo(var level: Level = Level.LOW, var lastUpdateTime: Date = Date()) {
    enum class Level { LOW, MEDIUM, HIGH }
}