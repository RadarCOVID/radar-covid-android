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
import org.funktionale.either.Either
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository(), ExampleRepository {

    override fun exampleRequest(): Either<Throwable, String> = callService {
        apiInterface.test()
    }

}