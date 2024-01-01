package com.erendev.gemini.domain.repository

import com.erendev.gemini.data.DataResult
import com.erendev.gemini.data.entity.ChatModel
import comerendevgemini.Chat
import comerendevgemini.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun generateContent(
        chat: ChatModel,
        text: String,
        list: List<Message>
    ): Flow<DataResult<List<Message>>>

    suspend fun getMessages(
        chat: ChatModel
    ): DataResult<List<Message>>

    suspend fun getChat(
        chatId: String
    ): DataResult<ChatModel?>

    suspend fun deleteChat(
        chat: ChatModel
    ): DataResult<Unit>

    suspend fun getRecent(page: Int): DataResult<List<ChatModel>>

    suspend fun getAllRecent(): DataResult<List<ChatModel>>

}