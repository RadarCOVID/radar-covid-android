package com.indra.coronaradar.datamanager.repository

import com.indra.coronaradar.models.domain.ExpositionInfo
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor() : DomainRepository {
    
    override var expositionInfo: ExpositionInfo? = null

}