package com.erendev.gemini.domain.usecase

import com.erendev.gemini.common.BaseUseCase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.GeminiResult
import com.erendev.gemini.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecentUseCase(
    private val chatRepository: ChatRepository
): BaseUseCase<Unit, List<ChatModel>>() {
    override suspend fun invoke(params: Unit) = performOperation {
        chatRepository.getAllRecent()
    }
}