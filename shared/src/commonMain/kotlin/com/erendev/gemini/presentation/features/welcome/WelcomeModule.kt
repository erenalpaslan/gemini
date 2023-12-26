package com.erendev.gemini.presentation.features.welcome

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val welcomeModule = module {
    factoryOf(::WelcomeViewModel)
}