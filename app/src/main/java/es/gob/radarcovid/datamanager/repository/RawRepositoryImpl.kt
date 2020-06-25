package es.gob.radarcovid.datamanager.repository

import android.content.Context
import android.content.res.AssetFileDescriptor
import com.google.android.gms.common.util.IOUtils
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.net.URI
import javax.inject.Named

class RawRepositoryImpl @javax.inject.Inject constructor(@Named("applicationContext") private val context: Context) : RawRepository {

    override fun getRawFileBytes(fileId: Int): ByteArray {

        context.getResources().openRawResource(fileId).use {
            return IOUtils.toByteArray(it)
        }

    }

}