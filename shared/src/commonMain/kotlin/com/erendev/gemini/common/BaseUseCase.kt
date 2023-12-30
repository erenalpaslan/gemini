package com.erendev.gemini.common

import com.erendev.gemini.data.DataResult
import com.erendev.gemini.domain.GeminiResult
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by erenalpaslan on 13.10.2023.
 */

abstract class BaseUseCase<I: Any?, O>: KoinComponent {

    private val appCoroutineDispatchers: AppCoroutineDispatchers by inject()

    abstract suspend operator fun invoke(params: I): Flow<GeminiResult<O>>

    suspend fun performFlowOperation(block: suspend () -> Flow<DataResult<O>>) = flow<GeminiResult<O>> {
        emit(GeminiResult.Loading(isLoading = true))
        try {
            block().collect { dataResult ->
                when(dataResult) {
                    is DataResult.Error -> emit(GeminiResult.Error(dataResult.message))
                    is DataResult.Success -> emit(GeminiResult.Success(dataResult.data))
                }
            }
        }catch (e: Exception) {
            emit(GeminiResult.Error(e.message))
        }
        emit(GeminiResult.Loading(isLoading = false))
    }.flowOn(appCoroutineDispatchers.io)

    suspend fun performOperation(block: suspend () -> DataResult<O>) = flow {
        emit(GeminiResult.Loading(isLoading = true))
        try {
            val result = block()
            when(result) {
                is DataResult.Error -> emit(GeminiResult.Error(result.message))
                is DataResult.Success -> emit(GeminiResult.Success(result.data))
            }
        }catch (e: Exception) {
            emit(GeminiResult.Error(e.message))
        }
        emit(GeminiResult.Loading(isLoading = false))
    }
}