package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.common.base.BaseRepository
import es.gob.radarcovid.datamanager.api.ContentfulInterface
import es.gob.radarcovid.models.response.ResponseLabels
import es.gob.radarcovid.models.response.ResponseLanguages
import org.funktionale.either.Either
import javax.inject.Inject

class ContentfulRepositoryImpl @Inject constructor(private val contentfulInterface: ContentfulInterface) :
    BaseRepository(), ContentfulRepository {

    override fun getLabels(language: String, region: String): Either<Throwable, ResponseLabels> =
        callService {
            contentfulInterface.getLabels(language, region)
        }

    override fun getLanguages(): Either<Throwable, ResponseLanguages> = callService {
        contentfulInterface.getLanguages()
    }

}