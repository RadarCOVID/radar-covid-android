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

import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.models.domain.Region
import es.gob.radarcovid.models.response.ResponseRegions
import es.gob.radarcovid.models.response.ResponseRegionsItem
import javax.inject.Inject

class RegionsDataMapper @Inject constructor(private val labelManager: LabelManager) {

    fun transform(responseRegions: ResponseRegions?): List<Region> = responseRegions?.let {
        it.map { responseRegionsItem -> transform(responseRegionsItem) }
    } ?: emptyList()

    private fun transform(responseRegionsItem: ResponseRegionsItem?): Region =
        responseRegionsItem?.let {
            Region(
                it.id ?: "",
                it.description ?: "",
                it.phone ?: labelManager.getContactPhone(),
                it.web ?: "",
                it.webName ?: it.web ?: ""
            )
        } ?: Region()

}