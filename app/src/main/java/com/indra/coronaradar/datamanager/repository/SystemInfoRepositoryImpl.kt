package com.indra.coronaradar.datamanager.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Named


class SystemInfoRepositoryImpl @Inject constructor(@Named("applicationContext") private val context: Context) :
    SystemInfoRepository {

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