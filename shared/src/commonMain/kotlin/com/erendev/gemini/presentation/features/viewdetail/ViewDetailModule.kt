package com.erendev.gemini.presentation.features.viewdetail

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewDetailModule = module {
    factoryOf(::ViewDetailViewModel)
}