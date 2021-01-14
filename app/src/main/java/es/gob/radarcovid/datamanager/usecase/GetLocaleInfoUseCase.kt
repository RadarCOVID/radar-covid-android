/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.base.Constants.SO_NAME
import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.LocaleInfo
import javax.inject.Inject

class GetLocaleInfoUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val preferencesRepository: PreferencesRepository
) {

    private val languageCodeCatalan = "ca-ES"
    private val languageCodeValencian = "va-ES"

    fun getLocaleInfo(): LocaleInfo = LocaleInfo(
        preferencesRepository.getLanguages(),
        preferencesRepository.getRegions()
    )

    fun getSelectedLanguage(): String =
        preferencesRepository.getSelectedLanguage()

    fun getSelectedLanguageForLocale(): String {
        val language = preferencesRepository.getSelectedLanguage()
        return if (language == languageCodeValencian) languageCodeCatalan else language
    }

    fun setSelectedLanguage(languageCode: String) {
        preferencesRepository.setSelectedLanguage(languageCode)
        preferencesRepository.setLanguageChanged(true)
    }

    fun isLanguageChanged(): Boolean =
        preferencesRepository.getLanguageChanged()

    fun resetLanguageChanged() =
        preferencesRepository.setLanguageChanged(false)

    fun setSelectedRegion(regionCode: String) =
        preferencesRepository.setSelectedRegion(regionCode)


    fun getLabels(onSuccess: (Map<String, String>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            apiRepository.getLabels(
                preferencesRepository.getSelectedLanguage(),
                preferencesRepository.getSelectedRegion(),
                SO_NAME,
                BuildConfig.VERSION_NAME
            )
        }
    }

}