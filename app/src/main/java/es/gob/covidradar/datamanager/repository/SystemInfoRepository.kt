package es.gob.covidradar.datamanager.repository

interface SystemInfoRepository {

    fun isInternetAvailable(): Boolean

}