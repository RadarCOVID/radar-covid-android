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

object Constants {

    const val SO_NAME = "Android"

    const val DATE_FORMAT = "dd/MM/yyyy"

    const val INCOMING_CODE_QUERY_PARAM = "code"
    const val HOST_REPORT = "report"
    const val HOST_QR_CODE = BuildConfig.QR_CODE_HOST_NAME

    const val NOTIFICATION_REMINDER_DEFAULT = 1440

    const val KPI_MATCH_CONFIRMED = "MATCH_CONFIRMED"
    const val ANALYTICS_PERIOD_DEFAULT = 1440

    //Encrypted shared preferences
    const val ENCRYPTED_PREFERENCES_NAME = "app_encrypted_preferences"
    const val ENCRYPTED_PREFERENCES_KEY_SIZE = 256

}