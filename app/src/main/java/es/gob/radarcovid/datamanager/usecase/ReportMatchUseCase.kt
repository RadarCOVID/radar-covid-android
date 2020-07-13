package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.extensions.toTimeStamp
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.models.request.RequestKpi
import es.gob.radarcovid.models.request.RequestKpiReport
import java.util.*
import javax.inject.Inject

class ReportMatchUseCase @Inject constructor(private val apiRepository: ApiRepository) {

    fun reportMatch() {
        asyncRequest({}, {}) {
            apiRepository.postKpiReport(RequestKpiReport().apply {
                add(RequestKpi(RequestKpi.MATCH_CONFIRMED, Date().toTimeStamp(), 1))
            })
        }
    }

}