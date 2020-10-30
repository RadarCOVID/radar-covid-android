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

    fun reloadLabels()

    fun showLanguageChangeDialog()

    fun showLanguageSelectionDialog(languages: List<String>, selectedIndex: Int)

    fun setLanguage(language: String)
}

interface LocaleSelectionPresenter {

    fun viewReady()

    fun onLanguageDropdownButtonClick()

    fun onLanguageSelectionChange(index: Int)

    fun onLanguagesListAcceptButtonClick(dialogDismissCallback: () -> Unit)

    fun onLocaleChangeConfirm()

}

interface LocaleSelectionRouter {

    fun restartApplication()

}