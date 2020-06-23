package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.ExposureInfo
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor() : DomainRepository {

    override var exposureInfo: ExposureInfo? = null

}