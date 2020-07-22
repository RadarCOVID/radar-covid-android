package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.LanguagesDataMapper
import es.gob.radarcovid.datamanager.mapper.RegionsDataMapper
import es.gob.radarcovid.datamanager.repository.ContentfulRepository
import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.Region
import javax.inject.Inject

class GetLocaleInfoUseCase @Inject constructor(
    private val contentfulRepository: ContentfulRepository,
    private val languagesDataMapper: LanguagesDataMapper,
    private val regionsDataMapper: RegionsDataMapper
) {

    fun getLanguages(onSuccess: (List<Language>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(contentfulRepository.getLanguages()) {
                languagesDataMapper.transform(it)
            }
        }
    }

    fun getRegions(onSuccess: (List<Region>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(contentfulRepository.getRegions()) {
                regionsDataMapper.transform(it)
            }
        }
    }

}