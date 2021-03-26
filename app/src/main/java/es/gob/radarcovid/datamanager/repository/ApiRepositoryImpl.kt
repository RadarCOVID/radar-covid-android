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
import es.gob.radarcovid.models.request.RequestKpi
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.*
import okhttp3.ResponseBody
import org.funktionale.either.Either
import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ApiRepository {

    override fun getSettings(): Either<Throwable, ResponseSettings> = callService {
        apiInterface.getSettings()
    }

    override fun getLabels(
        language: String,
        region: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseLabels> =
        callService {
            apiInterface.getLabels(language, region, platform, version)
        }

    override fun getLanguages(
        language: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseLanguages> =
        callService {
            apiInterface.getLanguages(language, platform, version)
        }

    override fun getRegions(
        language: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseRegions> =
        callService {
            apiInterface.getRegions(language, true, platform, version)
        }

    override fun verifyCode(
        body: RequestVerifyCode,
        sharingCode: String
    ): Either<Throwable, ResponseToken> =
        callService {
            apiInterface.verifyCode(body, sharingCode)
        }

    override fun getCountries(
        language: String,
        platform: String,
        version: String
    ): Either<Throwable, ResponseRegions> =
        callService {
            apiInterface.getCountries(language, platform, version)
        }

    override fun getStats(): Either<Throwable, ResponseStats> =
        callService {
            apiInterface.getStats()
        }

    override fun sendKpi(body: RequestKpi): Either<Throwable, String> =
        callService {
            apiInterface.sendKpi(body)
        }

    override fun getTraceKeys(keyBundleTag: Long): Response<ResponseBody> =
        apiInterface.getTraceKeys(keyBundleTag).execute()

}