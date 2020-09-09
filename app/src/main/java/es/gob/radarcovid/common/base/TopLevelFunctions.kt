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

import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.models.exception.MapperException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.funktionale.either.Either

inline fun <A, reified B> mapperScope(
    requestResult: Either<Throwable, A>,
    mapperFunction: (A) -> B
): Either<Throwable, B> {
    return try {
        if (requestResult.isLeft())
            Either.left(requestResult.left().get())
        else
            Either.right(mapperFunction(requestResult.right().get()))
    } catch (e: Exception) {
        if (BuildConfig.DEBUG)
            e.printStackTrace()
        Either.Left(
            MapperException(
                "Error when mapping to " + B::class.java.simpleName,
                e.message!!
            )
        )
    }
}

fun <T> asyncRequest(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    body: () -> Either<Throwable, T>
) {
    CoroutineScope(Dispatchers.Main).launch {
        val result = withContext(Dispatchers.IO) {
            body()
        }
        if (result.isRight())
            onSuccess(result.right().get())
        else
            onError(result.left().get())
    }
}