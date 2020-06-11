package com.indra.coronaradar.common.base

import com.indra.coronaradar.models.exception.UnknownException
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
                            Either.left(UnknownException())
                    }
                }
                else -> Either.left(UnknownException())
            }
        } catch (exception: Exception) {
            return Either.left(UnknownException())
        }
    }

}