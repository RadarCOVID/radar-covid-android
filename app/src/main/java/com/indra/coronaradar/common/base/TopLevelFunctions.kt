package com.indra.coronaradar.common.base

import com.indra.coronaradar.models.exception.MapperException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.funktionale.either.Either

inline fun <reified T> mapperScope(func: (Any) -> T): Either<Exception, T> {
    return try {
        val result = func.invoke(Any())
        Either.Right(result)
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(
            MapperException(
                "Error when mapping to " + T::class.java.simpleName,
                e.message!!
            )
        )
    }
}

fun <T> asyncRequest(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    body: () -> Either<Throwable, T>
) {
    CoroutineScope(Dispatchers.Main).launch {
        val result = withContext(Dispatchers.IO) {
            body()
        }
        if (result.isRight())
            onSuccess(result.right().get())
        else
            onError(result.left().get())
    }
}