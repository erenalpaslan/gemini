package com.erendev.gemini.presentation.features.home

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeModule = module {
    factoryOf(::HomeViewModel)
}