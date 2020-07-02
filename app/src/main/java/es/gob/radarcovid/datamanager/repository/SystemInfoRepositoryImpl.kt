package es.gob.radarcovid.datamanager.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

}