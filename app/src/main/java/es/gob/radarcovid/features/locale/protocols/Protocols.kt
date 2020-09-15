/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.locale.protocols

import es.gob.radarcovid.common.view.RequestView

interface LocaleSelectionView : RequestView {

    fun setRegions(regions: List<String>)

    fun setSelectedRegionIndex(index: Int)

    fun getSelectedRegionIndex(): Int

    fun setLanguages(languages: List<String>)

    fun setSelectedLanguageIndex(index: Int)

    fun getSelectedLanguageIndex(): Int

    fun reloadLabels()

    fun showLanguageChangeDialog()

}

interface LocaleSelectionPresenter {

    fun viewReady()

    fun onApplyButtonClick()

    fun onLanguageSelectionChange(index: Int)

    fun isLanguageChanged(): Boolean

    fun applyLocaleSettings()

    fun restoreLocaleSettings()

    fun onLocaleChangeConfirm()

}

interface LocaleSelectionRouter {

    fun restartApplication()

}