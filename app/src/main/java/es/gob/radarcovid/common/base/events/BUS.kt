/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.base.events

import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer

object BUS {

    private val bus = Bus(ThreadEnforcer.MAIN)

    fun register(listener: Any) {
        bus.register(listener)
    }

    fun unregister(listener: Any) {
        bus.unregister(listener)
    }

    fun post(event: Any) {
        bus.post(event)
    }

}