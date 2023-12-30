package com.erendev.gemini.data.database.dao

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import com.bumble.appyx.interactions.UUID
import com.erendev.gemini.AppDb
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import com.erendev.gemini.utils.randomUUID
import comerendevgemini.Chat
import comerendevgemini.Message
import kotlinx.coroutines.withContext

class ChatDao(
    private val appDb: AppDb,
    private val dispatchers: AppCoroutineDispatchers
) {

    private val query get() = appDb.appDatabaseQueries

    suspend fun getRecentChats(
        limit: Long = 10,
        offset: Long = 0
    ): List<Chat> {
        return withContext(dispatchers.io) {
            query.getRecentPages(limit, offset).awaitAsList()
        }
    }

    suspend fun getMessages(
        chatId: String
    ): List<Message> {
        return withContext(dispatchers.io) {
            query.getMessages(chatId).awaitAsList()
        }
    }

    suspend fun sendMessage(
        message: Message
    ) {
        withContext(dispatchers.io) {
            query.transaction {
                query.insertMessage(message)
            }
        }
    }

    suspend fun createChat(
        chat: Chat
    ) {
        withContext(dispatchers.io) {
            query.transaction {
                query.insertChat(chat)
            }
        }
    }

    suspend fun getChat(
        chatId: String
    ): Chat? {
        return withContext(dispatchers.io) {
            try {
                query.getChatById(chatId).awaitAsOne()
            }catch (e: NullPointerException) {
                null
            }catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun deleteChat(
        chatModel: ChatModel
    ) {
        withContext(dispatchers.io) {
            query.deleteMessagesByChatId(chatModel.chatId)
            query.deleteChatById(chatModel.chatId)
        }
    }
}