/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.mapper

import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.response.ResponseLanguages
import es.gob.radarcovid.models.response.ResponseLanguagesItem
import javax.inject.Inject

class LanguagesDataMapper @Inject constructor() {

    fun transform(responseLanguages: ResponseLanguages?): List<Language> = responseLanguages?.let {
        it.map { responseLanguagesItem -> transform(responseLanguagesItem) }
    } ?: emptyList()

    private fun transform(responseLanguagesItem: ResponseLanguagesItem?): Language =
        responseLanguagesItem?.let {
            Language(it.id ?: "", it.description ?: "")
        } ?: Language()

}