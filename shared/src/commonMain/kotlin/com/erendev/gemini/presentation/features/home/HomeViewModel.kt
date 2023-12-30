package com.erendev.gemini.presentation.features.home

import com.erendev.gemini.common.BaseViewModel
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.GeminiResult
import com.erendev.gemini.domain.usecase.DeleteChatUseCase
import com.erendev.gemini.domain.usecase.GetMessagesUseCase
import com.erendev.gemini.domain.usecase.GetRecentUseCase
import com.erendev.gemini.domain.usecase.SendMessageUseCase
import com.erendev.gemini.utils.date.DateUtils
import com.erendev.gemini.utils.randomUUID
import com.erendev.gemini.utils.settings.SettingKeys
import com.erendev.gemini.utils.settings.settings
import comerendevgemini.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val getRecentUseCase: GetRecentUseCase
) : BaseViewModel() {

    private lateinit var chat: ChatModel
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeChat()
    }

    fun onSend(text: String) {
        viewModelScope.launch {
            sendMessageUseCase(
                SendMessageUseCase.Param(
                    chat = chat,
                    text = text,
                    list = _uiState.value.messages
                )
            ).collect { result ->
                when (result) {
                    is GeminiResult.Loading -> _uiState.update { it.copy(onAnswering = result.isLoading) }
                    is GeminiResult.Success -> _uiState.update { it.copy(messages = result.data) }
                    is GeminiResult.Error -> {}
                }
            }
        }
    }

    private fun createNewChat() = ChatModel(
        chatId = randomUUID(),
        createdAt = DateUtils.now()
    )

    private fun initializeChat() {
        viewModelScope.launch {
            chat = settings.get<ChatModel>(SettingKeys.LAST_CHAT, null) ?: createNewChat()
            settings.store(SettingKeys.LAST_CHAT, chat)
            getMessages()
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            getMessagesUseCase(chat).collect { result ->
                when(result) {
                    is GeminiResult.Loading -> showProgress(result.isLoading)
                    is GeminiResult.Success -> _uiState.update { it.copy(messages = result.data) }
                    is GeminiResult.Error -> {}
                }
            }
        }
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            deleteChatUseCase(chat).collect { result ->
                when(result) {
                    is GeminiResult.Loading -> showProgress(result.isLoading)
                    is GeminiResult.Success -> {
                        chat = createNewChat()
                        settings.store(SettingKeys.LAST_CHAT, chat)
                        _uiState.update { it.copy(messages = emptyList()) }
                    }
                    is GeminiResult.Error -> {}
                }
            }
        }
    }

    fun newChatClicked() {
        viewModelScope.launch {
            chat = createNewChat()
            settings.store(SettingKeys.LAST_CHAT, chat)
            _uiState.update { it.copy(messages = emptyList()) }
        }
    }

    fun getRecent() {
        viewModelScope.launch {
            getRecentUseCase(0).collect { result ->
                when(result) {
                    is GeminiResult.Loading -> showProgress(result.isLoading)
                    is GeminiResult.Success -> _uiState.update { it.copy(recent = result.data) }
                    is GeminiResult.Error -> {}
                }
            }
        }
    }

    fun onChatClicked(chat: ChatModel) {
        viewModelScope.launch {
            this@HomeViewModel.chat = chat
            settings.store(SettingKeys.LAST_CHAT, chat)
            getMessages()
        }
    }
}