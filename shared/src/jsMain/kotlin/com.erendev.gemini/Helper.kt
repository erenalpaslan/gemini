package com.erendev.gemini

import com.erendev.gemini.data.network.initializeInterceptor
import com.erendev.gemini.di.appModule
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin

/**
 * Created by erenalpaslan on 30.09.2023
 */
fun initKoin(){
    startKoin {
        modules(appModule)
    }
    initializeInterceptor()
}