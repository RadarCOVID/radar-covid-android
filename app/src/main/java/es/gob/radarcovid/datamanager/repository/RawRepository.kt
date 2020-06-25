package es.gob.radarcovid.datamanager.repository


interface RawRepository {
    fun getRawFileBytes(fileId: Int): ByteArray
}