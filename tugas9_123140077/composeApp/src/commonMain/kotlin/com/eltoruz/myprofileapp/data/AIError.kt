package com.eltoruz.myprofileapp.data

import io.ktor.client.plugins.*
import kotlinx.coroutines.delay
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

sealed class AIError : Exception() {
    data class RateLimited(val retryAfter: Int) : AIError()
    data class Unauthorized(override val message: String) : AIError()
    data class ServerError(override val message: String) : AIError()
    data class NetworkError(override val message: String) : AIError()
    data class ParseError(override val message: String) : AIError()
}

suspend fun <T> safeAICall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: ClientRequestException) {
        when (e.response.status.value) {
            401 -> Result.failure(AIError.Unauthorized("Invalid API key"))
            429 -> {
                val retryAfter = e.response.headers["Retry-After"]?.toIntOrNull() ?: 60
                Result.failure(AIError.RateLimited(retryAfter))
            }
            in 500..599 -> Result.failure(AIError.ServerError("Server error"))
            else -> Result.failure(e)
        }
    } catch (e: IOException) {
        Result.failure(AIError.NetworkError("No internet connection"))
    } catch (e: SerializationException) {
        Result.failure(AIError.ParseError("Failed to parse response"))
    } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(AIError.NetworkError(e.message ?: "Terjadi kesalahan tak terduga"))
    }
}

suspend fun <T> retryWithBackoff(
    times: Int = 3,
    initialDelay: Long = 1000,
    maxDelay: Long = 10000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            when {
                e is AIError.RateLimited -> {
                    delay(e.retryAfter * 1000L)
                }
                e is AIError.ServerError -> {
                    delay(currentDelay)
                    currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
                }
                else -> throw e
            }
        }
    }
    return block()
}
