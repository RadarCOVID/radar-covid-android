package com.indra.coronaradar.datamanager.mapper

import com.indra.coronaradar.models.domain.ExpositionInfo
import org.dpppt.android.sdk.InfectionStatus
import org.dpppt.android.sdk.TracingStatus
import java.util.*
import javax.inject.Inject

class ExpositionInfoDataMapper @Inject constructor() {

    fun transform(tracingStatus: TracingStatus): ExpositionInfo =
        ExpositionInfo(transform(tracingStatus.infectionStatus), Date(tracingStatus.lastSyncDate))

    private fun transform(infectionStatus: InfectionStatus): ExpositionInfo.Level {
        return when (infectionStatus) {
            InfectionStatus.HEALTHY -> ExpositionInfo.Level.LOW
            InfectionStatus.EXPOSED -> ExpositionInfo.Level.MEDIUM
            InfectionStatus.INFECTED -> ExpositionInfo.Level.HIGH
        }
    }

}