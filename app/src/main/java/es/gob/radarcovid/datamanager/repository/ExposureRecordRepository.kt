package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.ExposureInfo

interface ExposureRecordRepository {

    fun getExposureRecords() : List<ExposureInfo>
    fun addExposure(exposureInfo: ExposureInfo)
    fun removeExposures()
}