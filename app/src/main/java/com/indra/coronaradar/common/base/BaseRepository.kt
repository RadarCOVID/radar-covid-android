package com.indra.coronaradar.common.base

import com.indra.coronaradar.models.exception.ServiceException
import org.funktionale.either.Either
import retrofit2.Call

abstract class BaseRepository {

    protected fun <T> callService(callback: () -> Call<T>): Either<Exception, T> {
        return try {
            val response = callback().execute()
            when {
                response.isSuccessful -> {
                    if (response.body() != null) {
                        Either.right(response.body()!!)
                    } else {
                        if (response.body() is Unit?)
                            Either.right(Unit as T)
                        else
                            Either.left(ServiceException.from(response))
                    }
                }
                else -> Either.left(ServiceException.from(response))
            }
        } catch (exception: Exception) {
            return Either.left(exception)
        }
    }

}