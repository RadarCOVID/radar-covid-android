package es.gob.radarcovid.models.exception

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.models.response.ResponseError
import okhttp3.ResponseBody
import retrofit2.Response


class ServiceException(message: String) : Exception(message) {

    companion object {

        private const val MESSAGE_DEFAULT: String = "Internal server error"

        private const val UNSPECIFIED_ERROR: String = "Can't get the detailed error message"

        fun <T> from(response: Response<T>): ServiceException = try {
            ServiceException("${response.code()} - ${getMessageFromErrorBody(response.errorBody())}")
        } catch (e: Exception) {
            if (BuildConfig.DEBUG)
                e.printStackTrace()
            ServiceException("")
        }


        private fun getMessageFromErrorBody(errorBody: ResponseBody?): String = errorBody?.let {
            Gson().fromJson<ResponseError>(
                it.charStream(),
                object : TypeToken<ResponseError>() {}.type
            ).message
        } ?: MESSAGE_DEFAULT


    }

}