package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.response.ResponseLanguages
import es.gob.radarcovid.models.response.ResponseLanguagesItem
import javax.inject.Inject

class LanguagesDataMapper @Inject constructor() {

    fun transform(responseLanguages: ResponseLanguages?): List<Language> = responseLanguages?.let {
        it.map { responseLanguagesItem -> transform(responseLanguagesItem) }
    } ?: emptyList()

    private fun transform(responseLanguagesItem: ResponseLanguagesItem?): Language =
        responseLanguagesItem?.let {
            Language(it.id ?: "", it.description ?: "")
        } ?: Language()

}