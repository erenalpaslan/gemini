package com.erendev.gemini.domain.usecase

import com.erendev.gemini.common.BaseUseCase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.repository.ChatRepository

class GetRecentUseCase(
    private val chatRepository: ChatRepository
): BaseUseCase<Int, List<ChatModel>>() {

    private var page: Int = 0

    override suspend fun invoke(params: Int) = performOperation {
        page = params
        chatRepository.getRecent(page)
    }

}