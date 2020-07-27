package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.response.ResponseLabels
import es.gob.radarcovid.models.response.ResponseLanguages
import es.gob.radarcovid.models.response.ResponseRegions
import org.funktionale.either.Either

interface ContentfulRepository {

    fun getLabels(language: String, region: String): Either<Throwable, ResponseLabels>

    fun getLanguages(language: String): Either<Throwable, ResponseLanguages>

    fun getRegions(language: String): Either<Throwable, ResponseRegions>

}