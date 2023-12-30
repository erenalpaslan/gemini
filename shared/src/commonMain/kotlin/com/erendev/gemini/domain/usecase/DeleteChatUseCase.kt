package com.erendev.gemini.domain.usecase

import com.erendev.gemini.common.BaseUseCase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.repository.ChatRepository

class DeleteChatUseCase(
    private val chatRepository: ChatRepository
): BaseUseCase<ChatModel, Unit>() {
    override suspend fun invoke(params: ChatModel) = performOperation {
        chatRepository.deleteChat(params)
    }
}