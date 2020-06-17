package com.indra.coronaradar.models.exception

import retrofit2.Response


class ServiceException(message: String) : Exception(message) {

    companion object {

        private const val MESSAGE_DEFAULT: String = "Internal server error"

        fun <T> from(response: Response<T>): ServiceException {
            val errorBody = response.errorBody()
            return if (errorBody != null && errorBody.string().isNotEmpty())
                ServiceException("${response.code()} - ${errorBody.string()}")
            else
                ServiceException("${response.code()} - $MESSAGE_DEFAULT")
        }

    }

}