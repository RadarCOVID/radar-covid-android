/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.models.exception

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.models.response.ResponseError
import okhttp3.ResponseBody
import retrofit2.Response


class ServiceException(message: String) : Exception(message) {

    companion object {

        private const val MESSAGE_DEFAULT: String = "Internal server error"
        
        fun <T> from(response: Response<T>): ServiceException = try {
            ServiceException("${response.code()} - ${getMessageFromErrorBody(response.errorBody())}")
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            ServiceException("")
        }


        private fun getMessageFromErrorBody(errorBody: ResponseBody?): String = errorBody?.let {
            Gson().fromJson<ResponseError>(
                it.charStream(),
                object : TypeToken<ResponseError>() {}.type
            ).message
        } ?: MESSAGE_DEFAULT


    }

}