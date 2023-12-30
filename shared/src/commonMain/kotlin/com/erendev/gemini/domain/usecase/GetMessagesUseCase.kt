package com.erendev.gemini.domain.usecase

import com.erendev.gemini.common.BaseUseCase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.repository.ChatRepository
import comerendevgemini.Chat
import comerendevgemini.Message

class GetMessagesUseCase(
    private val chatRepository: ChatRepository
): BaseUseCase<ChatModel, List<Message>>() {
    override suspend fun invoke(params: ChatModel) = performOperation {
        chatRepository.getMessages(params)
    }
}