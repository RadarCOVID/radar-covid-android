package es.gob.radarcovid.datamanager.repository


interface RawRepository {

    fun getRawFileString(fileId: Int): String

}