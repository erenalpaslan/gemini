package com.erendev.gemini.presentation.features.home

import com.erendev.gemini.data.entity.ChatModel
import comerendevgemini.Message

data class HomeUiState(
    val messages: List<Message> = emptyList(),
    val onAnswering: Boolean? = null,
    val recent: List<ChatModel> = emptyList(),
    val size: Int = 0
)
