package es.gob.covidradar.models.exception

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.covidradar.models.response.ResponseError
import okhttp3.ResponseBody
import retrofit2.Response


class ServiceException(message: String) : Exception(message) {

    companion object {

        private const val MESSAGE_DEFAULT: String = "Internal server error"

        fun <T> from(response: Response<T>): ServiceException =
            ServiceException("${response.code()} - ${getMessageFromErrorBody(response.errorBody())}")


        private fun getMessageFromErrorBody(errorBody: ResponseBody?): String = errorBody?.let {
            Gson().fromJson<ResponseError>(
                it.charStream(),
                object : TypeToken<ResponseError>() {}.type
            ).message
        } ?: MESSAGE_DEFAULT


    }

}