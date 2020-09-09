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
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject
import javax.inject.Named


class SystemInfoRepositoryImpl @Inject constructor(@Named("applicationContext") private val context: Context) :
    SystemInfoRepository {

    override fun getAndroidRelease(): String = Build.VERSION.RELEASE

    override fun getAndroidIncremental(): String = Build.VERSION.INCREMENTAL

    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        var isAvailable = false

        connectivityManager?.let {
            val capabilities =
                it.getNetworkCapabilities(it.activeNetwork)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                        isAvailable = true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                        isAvailable = true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                        isAvailable = true
                }
            }
        }

        return isAvailable
    }

}