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
    private val defaultSelectedIndex: Int =
        localeInfo.languages.indexOfFirst { it.code == getLocaleInfoUseCase.getSelectedLanguage() }
    private var selectedIndex: Int = defaultSelectedIndex

    override fun viewReady() {
        if(defaultSelectedIndex > -1) {
            view.setLanguage(localeInfo.languages[defaultSelectedIndex].name)
        }
    }

    override fun onLanguageSelectionChange(index: Int) {
        if (isLanguageChanged(index))
            selectedIndex = index
    }

    override fun isLanguageChanged(index: Int): Boolean =
        defaultSelectedIndex != index

    override fun onLocaleChangeConfirm() {
        applyLocaleSettings()
    }

    override fun applyLocaleSettings() {
        val selectedLanguage = localeInfo.languages[selectedIndex].code
        getLocaleInfoUseCase.setSelectedLanguage(selectedLanguage)
        router.restartApplication()
    }

    override fun restoreLocaleSettings() {
        view.setLanguage(localeInfo.languages[defaultSelectedIndex].name)
    }

    override fun onSelectLanguageClick() {
        view.showLanguageSelectionDialog(localeInfo.languages.map { it.name }, defaultSelectedIndex)
    }

    private fun requestLabels() {
        view.showLoading()
        getLocaleInfoUseCase.getLabels(
            onSuccess = {
                view.hideLoading()
            },
            onError = {
                view.hideLoading()
            }
        )
    }

}