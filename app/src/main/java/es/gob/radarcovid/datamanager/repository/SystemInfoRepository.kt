package es.gob.radarcovid.datamanager.repository

interface SystemInfoRepository {

    fun isInternetAvailable(): Boolean

}