package com.erendev.gemini.presentation.features.home

import com.erendev.gemini.common.BaseViewModel
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.domain.GeminiResult
import com.erendev.gemini.domain.usecase.DeleteChatUseCase
import com.erendev.gemini.domain.usecase.GetAllRecentUseCase
import com.erendev.gemini.domain.usecase.GetMessagesUseCase
import com.erendev.gemini.domain.usecase.GetRecentUseCase
import com.erendev.gemini.domain.usecase.SendMessageUseCase
import com.erendev.gemini.utils.date.DateUtils
import com.erendev.gemini.utils.randomUUID
import com.erendev.gemini.utils.settings.SettingKeys
import com.erendev.gemini.utils.settings.settings
import comerendevgemini.Chat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val getAllRecentUseCase: GetAllRecentUseCase
) : BaseViewModel() {

    private lateinit var chat: ChatModel
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var allRecent: List<ChatModel> = emptyList()
    private var searchText: MutableStateFlow<String?> = MutableStateFlow(null)
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val recent = searchText.mapLatest { searchText ->
        if (!searchText.isNullOrEmpty()) {
            allRecent.filter { it.title.lowercase().contains(searchText) }
        }else {
            allRecent
        }
    }.debounce(250)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

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
        createdAt = DateUtils.now(),
        title = ""
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
                when (result) {
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
                when (result) {
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
            getAllRecentUseCase(Unit).collect { result ->
                when (result) {
                    is GeminiResult.Loading -> showProgress(result.isLoading)
                    is GeminiResult.Success -> {
                        allRecent = result.data
                        searchText.update { "" }
                    }
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

    fun searchRecent(searchText: String?) {
        viewModelScope.launch {
            this@HomeViewModel.searchText.update { searchText }
        }
    }

}