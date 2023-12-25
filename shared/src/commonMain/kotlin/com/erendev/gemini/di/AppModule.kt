package com.erendev.gemini.di

import com.erendev.gemini.presentation.features.getstarted.getStartedModule
import com.erendev.gemini.presentation.features.splash.splashModule
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchersImpl
import org.koin.dsl.module

val appModule = module {
    single<AppCoroutineDispatchers> { AppCoroutineDispatchersImpl() }

    includes(
        splashModule,
        getStartedModule,
    )
}