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

import es.gob.radarcovid.common.base.BaseRepository
import es.gob.radarcovid.datamanager.api.ApiInterface
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.*
import org.funktionale.either.Either
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ApiRepository {

    override fun getUuid(): Either<Throwable, ResponseUuid> = callService {
        apiInterface.getUuid()
    }

    override fun getSettings(): Either<Throwable, ResponseSettings> = callService {
        apiInterface.getSettings()
    }

    override fun getLabels(
        uuid: String,
        language: String,
        region: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseLabels> =
        callService {
            apiInterface.getLabels(uuid, language, region, platform, version)
        }

    override fun getLanguages(
        uuid: String,
        language: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseLanguages> =
        callService {
            apiInterface.getLanguages(uuid, language, platform, version)
        }

    override fun getRegions(
        uuid: String,
        language: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseRegions> =
        callService {
            apiInterface.getRegions(uuid, language, true, platform, version)
        }

    override fun verifyCode(body: RequestVerifyCode): Either<Throwable, ResponseToken> =
        callService {
            apiInterface.verifyCode(body)
        }


}