package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.domain.Region
import es.gob.radarcovid.models.response.ResponseRegions
import es.gob.radarcovid.models.response.ResponseRegionsItem
import javax.inject.Inject

class RegionsDataMapper @Inject constructor() {

    fun transform(responseRegions: ResponseRegions?): List<Region> = responseRegions?.let {
        it.map { responseRegionsItem -> transform(responseRegionsItem) }
    } ?: emptyList()

    private fun transform(responseRegionsItem: ResponseRegionsItem?): Region =
        responseRegionsItem?.let {
            Region(it.id ?: "", it.description ?: "", it.phone ?: "", it.email ?: "")
        } ?: Region()

}