package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.models.domain.Region
import es.gob.radarcovid.models.response.ResponseRegions
import es.gob.radarcovid.models.response.ResponseRegionsItem
import javax.inject.Inject

class RegionsDataMapper @Inject constructor(private val labelManager: LabelManager) {

    fun transform(responseRegions: ResponseRegions?): List<Region> = responseRegions?.let {
        it.map { responseRegionsItem -> transform(responseRegionsItem) }
    } ?: emptyList()

    private fun transform(responseRegionsItem: ResponseRegionsItem?): Region =
        responseRegionsItem?.let {
            Region(
                it.id ?: "",
                it.description ?: "",
                it.phone ?: labelManager.getContactPhone(),
                it.web ?: "",
                it.webName ?: it.web ?: ""
            )
        } ?: Region()

}