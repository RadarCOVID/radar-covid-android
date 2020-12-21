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

import es.gob.radarcovid.datamanager.repository.ExposureStatusRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.domain.ExposureInfo
import javax.inject.Inject

class ExposureInfoUseCase @Inject constructor(
    private val repository: ExposureStatusRepository,
    private val preferencesRepository: PreferencesRepository
) {

    fun getExposureInfo(): ExposureInfo = repository.getExposureInfo()

    fun setExposed(exposed: Boolean) = preferencesRepository.setExposed(exposed)

    fun wasExposed(): Boolean = preferencesRepository.wasExposed()

    fun resetExposure() = repository.resetExposure()

}