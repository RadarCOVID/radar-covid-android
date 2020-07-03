package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.domain.ExposureInfo
import org.dpppt.android.sdk.InfectionStatus
import org.dpppt.android.sdk.TracingStatus
import org.dpppt.android.sdk.models.ExposureDay
import java.util.*
import javax.inject.Inject

class ExposureInfoDataMapper @Inject constructor() {

    fun transform(
        tracingStatus: TracingStatus,
        exposureDays: List<ExposureDay>?,
        infectionReportDate: Date?
    ): ExposureInfo =
        ExposureInfo(
            level = transform(tracingStatus.infectionStatus),
            lastUpdateTime = Date(tracingStatus.lastSyncDate),
            lastExposureDate = infectionReportDate ?: transform(exposureDays)
        )

    private fun transform(infectionStatus: InfectionStatus): ExposureInfo.Level {
        return when (infectionStatus) {
            InfectionStatus.HEALTHY -> ExposureInfo.Level.LOW
            InfectionStatus.EXPOSED -> ExposureInfo.Level.HIGH
            InfectionStatus.INFECTED -> ExposureInfo.Level.INFECTED
        }
    }

    private fun transform(exposureDays: List<ExposureDay>?): Date =
        exposureDays?.map { Date(it.exposedDate.startOfDayTimestamp) }?.max() ?: Date()

}