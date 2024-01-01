package com.erendev.gemini.data.entity

import comerendevgemini.Chat
import kotlinx.serialization.Serializable

@Serializable
data class ChatModel(
    val chatId: String,
    val createdAt: String,
    var title: String
)

fun Chat.toChatModel() = ChatModel(
    chatId = this.chatId,
    createdAt = this.createdAt,
    title = this.title
)

fun ChatModel.toChat() = Chat(
    chatId = this.chatId,
    createdAt = this.createdAt,
    title = this.title
)
