package com.erendev.gemini.common

import com.erendev.gemini.domain.GeminiResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by erenalpaslan on 13.10.2023.
 */

abstract class BaseUseCase<I: Any?, O>() {

    abstract operator fun invoke(params: I): Flow<GeminiResult<O>>

}