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

import android.content.Context
import android.os.Build
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.models.domain.Environment
import javax.inject.Inject
import javax.inject.Named

class BuildInfoRepositoryImpl @Inject constructor(
    @Named("applicationContext") private val context: Context
) : BuildInfoRepository {

    override fun getEnvironment(): Environment = when {
        BuildConfig.PRE -> Environment.PRE
        else -> Environment.PRO
    }

    override fun getEnvironmentBaseUrl(): String = BuildConfig.API_URL

    @Suppress("DEPRECATION")
    override fun getVersionCode(): Int {
        context.packageManager.getPackageInfo(context.packageName, 0).let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.longVersionCode.toInt() // avoid huge version numbers and you will be ok
            } else {
                it.versionCode
            }
        }
    }

    override fun getVersionName(): String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName

    override fun getPackageName(): String = context.packageName

}