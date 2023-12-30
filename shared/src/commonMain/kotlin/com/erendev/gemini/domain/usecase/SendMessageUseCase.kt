package com.erendev.gemini.domain.usecase

import com.erendev.gemini.common.BaseUseCase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.repository.ChatRepository
import comerendevgemini.Chat
import comerendevgemini.Message

class SendMessageUseCase(
    private val chatRepository: ChatRepository
): BaseUseCase<SendMessageUseCase.Param, List<Message>>() {

    data class Param(
        val chat: ChatModel,
        val text: String,
        val list: List<Message>
    )

    override suspend fun invoke(params: Param) = performFlowOperation {
        chatRepository.generateContent(
            chat = params.chat,
            list = params.list,
            text = params.text
        )
    }

}