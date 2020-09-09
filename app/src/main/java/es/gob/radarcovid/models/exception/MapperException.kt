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

class MapperException(
    private val title: String,
    private val detail: String = "The types in the class fields do not match"
) : Exception() {
    override val message: String?
        get() = title

    override val cause: Throwable?
        get() = Throwable(detail)
}