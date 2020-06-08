package com.indra.contacttracing.datamanager.repository

import com.indra.contacttracing.models.domain.ExpositionInfo
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor() : DomainRepository {
    
    override var expositionInfo: ExpositionInfo? = null

}