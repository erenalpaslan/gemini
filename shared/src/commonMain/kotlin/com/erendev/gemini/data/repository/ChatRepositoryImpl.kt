package com.erendev.gemini.data.repository

import com.erendev.gemini.data.DataResult
import com.erendev.gemini.data.database.dao.ChatDao
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.data.entity.GeminiContent
import com.erendev.gemini.data.entity.GeminiMessage
import com.erendev.gemini.data.entity.toChat
import com.erendev.gemini.data.entity.toChatModel
import com.erendev.gemini.data.network.services.GeminiAPIService
import com.erendev.gemini.domain.repository.ChatRepository
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import com.erendev.gemini.utils.randomUUID
import comerendevgemini.Chat
import comerendevgemini.Message
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChatRepositoryImpl(
    private val chatDao: ChatDao,
    private val geminiAPIService: GeminiAPIService,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ChatRepository {

    override suspend fun generateContent(
        chat: ChatModel,
        text: String,
        list: List<Message>
    ) = flow {
        chatDao.createChat(chat.toChat())
        val userMessage = Message(
            messageId = randomUUID(),
            chatId = chat.chatId,
            content = text,
            isAiResponse = false,
            timestamp = ""
        )
        val newList = list + userMessage
        emit(DataResult.Success(newList))

        val geminiContent = newList.map {
            GeminiContent(
                role = if (it.isAiResponse) "model" else "user",
                parts = listOf(GeminiMessage(it.content))
            )
        }.toMutableList()
        when (val response = geminiAPIService.generateContent(geminiContent)) {
            is DataResult.Error -> emit(DataResult.Error(response.message))
            is DataResult.Success -> {
                val modelMessage = Message(
                    messageId = randomUUID(),
                    chatId = chat.chatId,
                    content = response.data.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "",
                    isAiResponse = true,
                    timestamp = ""
                )
                chatDao.sendMessage(userMessage)
                chatDao.sendMessage(modelMessage)
                emit(DataResult.Success(newList + modelMessage))
            }
        }

    }.flowOn(appCoroutineDispatchers.io)

    override suspend fun getMessages(chat: ChatModel): DataResult<List<Message>> {
        return try {
            val messages = chatDao.getMessages(chat.chatId)
            DataResult.Success(messages)
        }catch (e: Exception) {
            DataResult.Error(e.message)
        }
    }

    override suspend fun getChat(chatId: String): DataResult<ChatModel?> {
        return try {
            val chat = chatDao.getChat(chatId)
            DataResult.Success(chat?.toChatModel())
        }catch (e: Exception) {
            DataResult.Error(e.message)
        }
    }

    override suspend fun deleteChat(chat: ChatModel): DataResult<Unit> {
        return try {
            chatDao.deleteChat(chat)
            DataResult.Success(Unit)
        }catch (e: Exception) {
            DataResult.Error(e.message)
        }
    }

}