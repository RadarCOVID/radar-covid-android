package com.indra.coronaradar.datamanager.repository

interface SystemInfoRepository {

    fun isInternetAvailable(): Boolean

}