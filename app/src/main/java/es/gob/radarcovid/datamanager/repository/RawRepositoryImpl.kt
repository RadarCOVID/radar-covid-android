package es.gob.radarcovid.datamanager.repository

import android.content.Context
import com.google.android.gms.common.util.IOUtils
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets
import javax.inject.Named

class RawRepositoryImpl @javax.inject.Inject constructor(@Named("applicationContext") private val context: Context) : RawRepository {

    override fun getRawFileBytes(fileId: Int): ByteArray {

        context.getResources().openRawResource(fileId).use {
            return IOUtils.toByteArray(it)
        }

    }

    override fun getRawFileString(fileId: Int): String {
        context.getResources().openRawResource(fileId).use {
            val bufferSize = 1024
            val buffer = CharArray(bufferSize)
            val out = StringBuilder()
            val reader: Reader = InputStreamReader(it, StandardCharsets.UTF_8)
            var charsRead: Int = 0
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