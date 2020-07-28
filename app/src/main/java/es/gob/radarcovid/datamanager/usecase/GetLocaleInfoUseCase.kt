package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.LocaleInfo
import javax.inject.Inject

class GetLocaleInfoUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository
) {

    fun getLocaleInfo(): LocaleInfo = LocaleInfo(
        preferencesRepository.getLanguages(),
        preferencesRepository.getRegions()
    )

    fun setSelectedLanguage(languageCode: String) =
        preferencesRepository.setSelectedLanguage(languageCode)


    fun setSelectedRegion(regionCode: String) =
        preferencesRepository.setSelectedRegion(regionCode)


    fun getLabels(onSuccess: (Map<String, String>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            apiRepository.getLabels(
                preferencesRepository.getSelectedLanguage(),
                preferencesRepository.getSelectedRegion()
            )
        }
    }

}