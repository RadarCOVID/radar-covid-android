package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.ExposureInfo

interface DomainRepository {

    var exposureInfo: ExposureInfo?

}