package com.erendev.gemini.data.network

import com.erendev.gemini.data.network.services.GeminiAPIService
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val networkModule = module {
    factoryOf(::GeminiAPIService)
}