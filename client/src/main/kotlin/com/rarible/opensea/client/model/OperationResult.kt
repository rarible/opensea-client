package com.rarible.opensea.client.model

import com.rarible.opensea.client.model.OperationResult.Companion.fail
import com.rarible.opensea.client.model.OperationResult.Companion.success

sealed class OperationResult<out V, out E> {

    val isSuccess: Boolean get() = this is Success

    val isFailure: Boolean get() = !isSuccess

    data class Success<out V>(val result: V) : OperationResult<V, Nothing>()

    data class Fail<out E>(val error: E) : OperationResult<Nothing, E>()

    fun ensureSuccess(): V = when (this) {
        is Success -> result
        is Fail -> {
            if (error is Throwable) {
                throw error
            } else {
                throw IllegalStateException("Operation finished with error $error")
            }
        }
    }

    fun getOrNull(): V? = when (this) {
        is Fail -> null
        is Success -> result
    }

    fun errorOrNull(): E? = when (this) {
        is Fail -> error
        is Success -> null
    }

    inline fun getOrThrow(exceptionBuilder: (E) -> Throwable): V = when (this) {
        is Success -> result
        is Fail -> throw exceptionBuilder(error)
    }

    inline fun <T> map(transform: (V) -> T): OperationResult<T, E> = when (this) {
        is Success -> success(transform(result))
        is Fail -> fail(error)
    }

    companion object {
        fun <V> success(value: V): OperationResult<V, Nothing> =
            Success(value)

        fun <E> fail(value: E): OperationResult<Nothing, E> =
            Fail(value)
    }
}

fun <R, V : R> OperationResult<V, *>.getOrDefault(default: R): R = when (this) {
    is OperationResult.Fail -> default
    is OperationResult.Success -> result
}

inline fun <V, E, T> OperationResult<V, E>.flatMap(transform: (V) -> OperationResult<T, E>): OperationResult<T, E> =
    when (this) {
        is OperationResult.Success -> transform(result)
        is OperationResult.Fail -> fail(error)
    }

inline fun <V, E, R> OperationResult<V, E>.onFailure(action: (E) -> R): OperationResult<V, R> = when (this) {
    is OperationResult.Success -> success(result)
    is OperationResult.Fail -> fail(action(error))
}

inline fun <V, E, R> OperationResult<V, E>.onSuccess(action: (V) -> R): OperationResult<R, E> = when (this) {
    is OperationResult.Success -> success(action(result))
    is OperationResult.Fail -> fail(error)
}
