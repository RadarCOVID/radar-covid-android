package es.gob.covidradar.datamanager.repository

import es.gob.covidradar.models.domain.ExposureInfo
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor() : DomainRepository {

    override var exposureInfo: ExposureInfo? = null

}