package com.indra.coronaradar.datamanager.mapper

import com.indra.coronaradar.models.domain.ExposureInfo
import org.dpppt.android.sdk.InfectionStatus
import org.dpppt.android.sdk.TracingStatus
import java.util.*
import javax.inject.Inject

class ExpositionInfoDataMapper @Inject constructor() {

    fun transform(tracingStatus: TracingStatus): ExposureInfo =
        ExposureInfo(transform(tracingStatus.infectionStatus), Date(tracingStatus.lastSyncDate))

    private fun transform(infectionStatus: InfectionStatus): ExposureInfo.Level {
        return when (infectionStatus) {
            InfectionStatus.HEALTHY -> ExposureInfo.Level.LOW
            InfectionStatus.EXPOSED -> ExposureInfo.Level.MEDIUM
            InfectionStatus.INFECTED -> ExposureInfo.Level.HIGH
        }
    }

}