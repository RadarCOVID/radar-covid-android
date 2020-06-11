package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.models.domain.ExposureInfo
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor() : DomainRepository {

    override var exposureInfo: ExposureInfo? = null

}