package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.LocaleInfo
import javax.inject.Inject

class GetLocaleInfoUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    fun getLocaleInfo(): LocaleInfo = LocaleInfo(
        preferencesRepository.getLanguages(),
        preferencesRepository.getRegions()
    )

}