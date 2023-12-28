package com.erendev.gemini.data.database.dao

import com.bumble.appyx.interactions.UUID
import com.erendev.gemini.AppDb
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
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
            query.getRecentPages(limit, offset).executeAsList()
        }
    }

    suspend fun getMessages(
        chatId: String
    ): List<Message> {
        return withContext(dispatchers.io) {
            query.getMessages(chatId).executeAsList()
        }
    }

    suspend fun sendMessage(
        chat: Chat,
        content: String,
        isAiResponse: Boolean
    ): Message? {
        withContext(dispatchers.io) {
            query.transaction {

            }
        }
       return null
    }
}