package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.common.base.BaseRepository
import es.gob.radarcovid.datamanager.api.ApiInterface
import es.gob.radarcovid.models.response.ResponseLabels
import es.gob.radarcovid.models.response.ResponseLanguages
import es.gob.radarcovid.models.response.ResponseRegions
import org.funktionale.either.Either
import javax.inject.Inject

class ContentfulRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) :
    BaseRepository(), ContentfulRepository {

    override fun getLabels(language: String, region: String): Either<Throwable, ResponseLabels> =
        callService {
            apiInterface.getLabels(region, language)
        }

    override fun getLanguages(language: String): Either<Throwable, ResponseLanguages> =
        callService {
            apiInterface.getLanguages(language)
        }

    override fun getRegions(language: String): Either<Throwable, ResponseRegions> =
        callService {
            apiInterface.getRegions(language)
        }

}