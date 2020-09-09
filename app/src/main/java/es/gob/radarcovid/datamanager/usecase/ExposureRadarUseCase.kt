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

import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import javax.inject.Inject

class ExposureRadarUseCase @Inject constructor(private val repository: ContactTracingRepository) {

    fun setRadarEnabled(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        onCancelled: () -> Unit
    ) = repository.startRadar(onSuccess, onError, onCancelled)


    fun setRadarDisabled() = repository.stopRadar()

    fun isRadarEnabled() = repository.isRadarEnabled()

}