package com.erendev.gemini.presentation.features.home

import comerendevgemini.Message

data class HomeUiState(
    val messages: List<Message> = emptyList(),
    val onAnswering: Boolean? = null,
    val size: Int = 0
)
