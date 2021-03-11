/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.api

import es.gob.radarcovid.models.request.RequestKpi
import es.gob.radarcovid.models.request.RequestVerifyCode
import es.gob.radarcovid.models.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    companion object {

        const val EFGS_SHARING = "X-EFGS-Sharing"

    }

    @GET("/")
    fun test(): Call<String>

    @GET("/configuration/settings")
    fun getSettings(): Call<ResponseSettings>

    @GET("/configuration/texts")
    fun getLabels(
        @Query("locale") language: String,
        @Query("ccaa") region: String,
        @Query("platform") platform: String,
        @Query("version") version: String
    ): Call<ResponseLabels>

    @GET("/configuration/masterData/locales")
    fun getLanguages(
        @Query("locale") language: String,
        @Query("platform") platform: String,
        @Query("version") version: String
    ): Call<ResponseLanguages>

    @GET("/configuration/masterData/ccaa")
    fun getRegions(
        @Query("locale") language: String,
        @Query("additionalInfo") additionalInfo: Boolean,
        @Query("platform") platform: String,
        @Query("version") version: String
    ): Call<ResponseRegions>

    @POST("/verification/verify/code")
    fun verifyCode(
        @Body body: RequestVerifyCode,
        @Header(EFGS_SHARING) sharingEFGSHeader: String
    ): Call<ResponseToken>

    @GET("/configuration/masterData/countries")
    fun getCountries(
        @Query("locale") language: String,
        @Query("platform") platform: String,
        @Query("version") version: String
    ): Call<ResponseRegions>

    @GET("kpi/statistics/basics")
    fun getStats(): Call<ResponseStats>

    @POST("/kpi/google")
    fun sendKpi(
        @Body body: RequestKpi
    ): Call<String>

    @Headers("Accept: application/x-protobuf")
    @GET("/notifyme/v1/traceKeys ")
    fun getTraceKeys(
        @Query("keyBundleTag") keyBundleTag: Long
    ): Call<ResponseBody>
}