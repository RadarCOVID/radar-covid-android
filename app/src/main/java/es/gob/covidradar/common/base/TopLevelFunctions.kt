package es.gob.covidradar.common.base

import es.gob.covidradar.models.exception.MapperException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.funktionale.either.Either

inline fun <A, reified B> mapperScope(
    requestResult: Either<Throwable, A>,
    mapperFunction: (A) -> B
): Either<Throwable, B> {
    return try {
        if (requestResult.isLeft())
            Either.left(requestResult.left().get())
        else
            Either.right(mapperFunction(requestResult.right().get()))
    } catch (e: Exception) {
        e.printStackTrace()
        Either.Left(
            MapperException(
                "Error when mapping to " + B::class.java.simpleName,
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