package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.ExposureInfo
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.radarcovid.common.extensions.toJson
import javax.inject.Inject

class ExposureRecordRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences?
): ExposureRecordRepository {

    companion object {
        private const val KEY_EXPOSURE_RECORD_LIST = "key_exposure_record_list"
    }

    private fun saveToPrefs(key: String, data: String) {
        sharedPreferences?.edit()?.putString(key, data)?.apply()
    }

    private fun getFromPreferences(key: String): String? =
        sharedPreferences?.getString(key, null)

    private fun removeFromPrefs(key: String) {
        sharedPreferences?.edit()?.remove(key)?.apply()
    }


    override fun getExposureRecords(): List<ExposureInfo> {
        val list = getFromPreferences(KEY_EXPOSURE_RECORD_LIST)
        return if (list != null) {
            val itemType = object : TypeToken<List<ExposureInfo>>() {}.type
            Gson().fromJson(
                list,
                itemType
            )
        } else {
            emptyList()
        }
    }

    override fun addExposure(exposureInfo: ExposureInfo) {
        var list = getExposureRecords()
        list += exposureInfo
        saveToPrefs(KEY_EXPOSURE_RECORD_LIST, list.toJson())
    }

    override fun removeExposures() {
        removeFromPrefs(KEY_EXPOSURE_RECORD_LIST)
    }


}