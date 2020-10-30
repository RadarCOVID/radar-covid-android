/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.locale.presenter

import es.gob.radarcovid.datamanager.usecase.GetLocaleInfoUseCase
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionRouter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import es.gob.radarcovid.models.domain.LocaleInfo
import javax.inject.Inject

class LocaleSelectionPresenterImpl @Inject constructor(
    private val view: LocaleSelectionView,
    private val router: LocaleSelectionRouter,
    private val getLocaleInfoUseCase: GetLocaleInfoUseCase
) : LocaleSelectionPresenter {

    private val localeInfo: LocaleInfo = getLocaleInfoUseCase.getLocaleInfo()
    private val currentLanguageIndex: Int =
        localeInfo.languages.indexOfFirst { it.code == getLocaleInfoUseCase.getSelectedLanguage() }
    private var selectedLanguageIndex: Int = currentLanguageIndex

    override fun viewReady() {
        if (currentLanguageIndex > -1)
            view.setLanguage(localeInfo.languages[currentLanguageIndex].name)
    }

    override fun onLanguageDropdownButtonClick() {
        selectedLanguageIndex = currentLanguageIndex
        view.showLanguageSelectionDialog(localeInfo.languages.map { it.name }, currentLanguageIndex)
    }

    override fun onLanguageSelectionChange(index: Int) {
        selectedLanguageIndex = index
    }

    override fun onLanguagesListAcceptButtonClick(dialogDismissCallback: () -> Unit) {
        val selectedLanguageCode = localeInfo.languages[selectedLanguageIndex].code
        val currentLanguage = getLocaleInfoUseCase.getSelectedLanguage()
        if (selectedLanguageCode == currentLanguage)
            dialogDismissCallback()
        else
            view.showLanguageChangeDialog()
    }

    override fun onLocaleChangeConfirm() {
        applyLocaleSettings()
    }

    private fun applyLocaleSettings() {
        val selectedLanguage = localeInfo.languages[selectedLanguageIndex].code
        getLocaleInfoUseCase.setSelectedLanguage(selectedLanguage)
        router.restartApplication()
    }

}