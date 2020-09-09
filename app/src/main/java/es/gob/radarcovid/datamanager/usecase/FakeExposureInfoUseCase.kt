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

import es.gob.radarcovid.datamanager.repository.BuildInfoRepository
import es.gob.radarcovid.datamanager.repository.ExposureStatusRepository
import es.gob.radarcovid.models.domain.Environment
import javax.inject.Inject

class FakeExposureInfoUseCase @Inject constructor(
    private val exposureStatusRepository: ExposureStatusRepository,
    private val buildInfoRepository: BuildInfoRepository
) {

    fun addFakeExposureDay() {
        exposureStatusRepository.addFakeExposureDate()
    }

    fun getEnvironment(): Environment = buildInfoRepository.getEnvironment()

}