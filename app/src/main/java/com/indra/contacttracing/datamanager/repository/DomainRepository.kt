package com.indra.contacttracing.datamanager.repository

import com.indra.contacttracing.models.domain.ExpositionInfo

interface DomainRepository {

    var expositionInfo: ExpositionInfo?

}