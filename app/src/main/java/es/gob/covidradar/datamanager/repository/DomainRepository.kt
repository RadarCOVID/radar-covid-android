package es.gob.covidradar.datamanager.repository

import es.gob.covidradar.models.domain.ExposureInfo

interface DomainRepository {

    var exposureInfo: ExposureInfo?

}