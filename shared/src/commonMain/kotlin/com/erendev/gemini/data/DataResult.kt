package com.erendev.gemini.data

import com.erendev.gemini.domain.GeminiResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

sealed interface DataResult <T> {

    data class Success<T>(val data: T): DataResult<T>

    data class Error<T>(val message: String?): DataResult<T>

}


suspend inline fun <reified T> HttpResponse.toDataResult(): DataResult<T> {
    return try {
        DataResult.Success(this.body<T>())
    }catch (e: Exception) {
        DataResult.Error(e.message)
    }
}