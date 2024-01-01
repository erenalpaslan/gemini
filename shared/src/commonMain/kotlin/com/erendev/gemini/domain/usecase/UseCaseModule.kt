package com.erendev.gemini.domain.usecase

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::SendMessageUseCase)
    factoryOf(::GetMessagesUseCase)
    factoryOf(::DeleteChatUseCase)
    factoryOf(::GetRecentUseCase)
    factoryOf(::GetAllRecentUseCase)
}