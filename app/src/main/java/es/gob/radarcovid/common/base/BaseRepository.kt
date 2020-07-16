package es.gob.radarcovid.common.base

import es.gob.radarcovid.models.exception.GenericRequestException
import es.gob.radarcovid.models.exception.ServiceException
import org.funktionale.either.Either
import retrofit2.Call

abstract class BaseRepository {

    protected fun <T> callService(call: () -> Call<T>): Either<Exception, T> {
        return try {
            val response = call().execute()
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
            return Either.left(GenericRequestException())
        }
    }

}