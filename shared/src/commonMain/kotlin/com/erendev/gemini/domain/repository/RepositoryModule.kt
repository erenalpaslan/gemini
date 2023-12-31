package com.erendev.gemini.domain.repository

import com.erendev.gemini.data.repository.ChatRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<ChatRepository>{ ChatRepositoryImpl(get(), get()) }
}