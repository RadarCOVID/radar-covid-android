package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.response.ResponseLabels
import org.funktionale.either.Either

interface ContentfulRepository {

    fun getLabels(locale: String): Either<Throwable, ResponseLabels>

}