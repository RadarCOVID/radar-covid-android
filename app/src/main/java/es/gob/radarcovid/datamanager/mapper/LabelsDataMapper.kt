package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.response.ResponseLabels
import es.gob.radarcovid.models.response.ResponseLabelsEntry
import es.gob.radarcovid.models.response.ResponseLabelsIncludes
import javax.inject.Inject

class LabelsDataMapper @Inject constructor() {

    fun transform(responseLabels: ResponseLabels): Map<String, String> =
        transformIncludes(responseLabels.includes)

    private fun transformIncludes(includes: ResponseLabelsIncludes?): Map<String, String> =
        (includes?.let { include ->
            transformEntries(include.Entry)
        } ?: emptyMap())


    private fun transformEntries(entries: List<ResponseLabelsEntry>?): Map<String, String> =
        entries?.let {
            val labels: MutableMap<String, String> = HashMap()
            entries.forEach { entry ->
                if (entry.fields != null)
                    labels.putAll(entry.fields)
            }
            labels
        } ?: emptyMap()


}