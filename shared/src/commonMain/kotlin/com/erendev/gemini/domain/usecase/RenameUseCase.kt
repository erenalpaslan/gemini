package com.erendev.gemini.domain.usecase

import com.erendev.gemini.common.BaseUseCase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.GeminiResult
import com.erendev.gemini.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class RenameUseCase(
    private val chatRepository: ChatRepository
): BaseUseCase<RenameUseCase.Param, Unit>() {

    data class Param(
        val chat: ChatModel,
        val title: String
    )

    override suspend fun invoke(params: Param) = performOperation {
        chatRepository.rename(params.chat, params.title)
    }

}