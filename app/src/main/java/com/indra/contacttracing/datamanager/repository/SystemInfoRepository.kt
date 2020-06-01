package com.indra.contacttracing.datamanager.repository

interface SystemInfoRepository {

    fun isInternetAvailable(): Boolean

}