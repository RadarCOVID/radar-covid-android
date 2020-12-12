/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.*
import org.funktionale.either.Either

interface ApiRepository {

    fun getUuid(): Either<Throwable, ResponseUuid>

    fun getSettings(): Either<Throwable, ResponseSettings>

    fun getLabels(uuid: String, language: String, region: String, platform: String, version: String): Either<Throwable, ResponseLabels>

    fun getLanguages(uuid: String, language: String, platform: String, version: String): Either<Throwable, ResponseLanguages>

    fun getRegions(uuid: String, language: String, platform: String, version: String): Either<Throwable, ResponseRegions>

    fun verifyCode(body: RequestVerifyCode, sharingCode: String): Either<Throwable, ResponseToken>

}