/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.base

import es.gob.radarcovid.models.exception.GenericRequestException
import es.gob.radarcovid.models.exception.NetworkUnavailableException
import es.gob.radarcovid.models.exception.ServiceException
import org.funktionale.either.Either
import retrofit2.Call
import java.net.UnknownHostException

abstract class BaseRepository {

    protected fun <T> callService(call: () -> Call<T>): Either<Exception, T> {
        return try {
            val response = call().execute()
            when {
                response.isSuccessful -> {
                    if (response.body() != null) {
                        Either.right(response.body()!!)
                    } else {
                        if (response.body() is Unit?) {
                            Either.right(Unit as T)
                        }
                        else {
                            Either.left(ServiceException.from(response))
                        }
                    }
                }
                else -> Either.left(ServiceException.from(response))
            }
        } catch (exception: Exception) {
            return if (exception is UnknownHostException) {
                Either.left(NetworkUnavailableException())
            } else {
                Either.left(GenericRequestException())
            }
        }
    }

}