package es.gob.radarcovid.datamanager.repository

import android.content.Context
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Named

class RawRepositoryImpl @Inject constructor(@Named("applicationContext") private val context: Context) :
    RawRepository {


    override fun getRawFileString(fileId: Int): String {
        context.resources.openRawResource(fileId).use {
            val bufferSize = 1024
            val buffer = CharArray(bufferSize)
            val out = StringBuilder()
            val reader: Reader = InputStreamReader(it, StandardCharsets.UTF_8)
            var charsRead: Int
            do {
                charsRead = reader.read(buffer, 0, buffer.size)
                if (charsRead > 0) {
                    out.append(buffer, 0, charsRead)
                } else {
                    break
                }
            } while (true)

            return out.toString()
        }
    }

}