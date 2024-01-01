package com.erendev.gemini.data.database.dao

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import com.bumble.appyx.interactions.UUID
import com.erendev.gemini.AppDb
import com.erendev.gemini.data.database.AppDatabase
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import com.erendev.gemini.utils.randomUUID
import comerendevgemini.Chat
import comerendevgemini.Message
import kotlinx.coroutines.withContext

class ChatDao(
    private val dispatchers: AppCoroutineDispatchers
) {

    suspend fun getRecentChats(
        limit: Long = 10,
        offset: Long = 0
    ): List<Chat> {
        return withContext(dispatchers.io) {
            AppDatabase.appDb?.appDatabaseQueries?.getRecentPages(limit, offset)?.awaitAsList() ?: emptyList()
        }
    }

    suspend fun getMessages(
        chatId: String
    ): List<Message> {
        return withContext(dispatchers.io) {
            AppDatabase.appDb?.appDatabaseQueries?.getMessages(chatId)?.awaitAsList() ?: emptyList()
        }
    }

    suspend fun sendMessage(
        message: Message
    ) {
        withContext(dispatchers.io) {
            AppDatabase.appDb?.appDatabaseQueries?.transaction {
                AppDatabase.appDb?.appDatabaseQueries?.insertMessage(message)
            }
        }
    }

    suspend fun createChat(
        chat: Chat
    ) {
        withContext(dispatchers.io) {
            AppDatabase.appDb?.appDatabaseQueries?.transaction {
                AppDatabase.appDb?.appDatabaseQueries?.insertChat(chat)
            }
        }
    }

    suspend fun getChat(
        chatId: String
    ): Chat? {
        return withContext(dispatchers.io) {
            try {
                AppDatabase.appDb?.appDatabaseQueries?.getChatById(chatId)?.awaitAsOne()
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
            AppDatabase.appDb?.appDatabaseQueries?.deleteMessagesByChatId(chatModel.chatId)
            AppDatabase.appDb?.appDatabaseQueries?.deleteChatById(chatModel.chatId)
        }
    }

    suspend fun getAllRecent(): List<Chat> {
        return withContext(dispatchers.io) {
            AppDatabase.appDb?.appDatabaseQueries?.getAllRecentPages()?.awaitAsList() ?: emptyList()
        }
    }
}