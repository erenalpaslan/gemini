package com.erendev.gemini.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import co.touchlab.kermit.Logger
import com.bumble.appyx.components.backstack.operation.push
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.components.GeminiDrawerSheet
import com.erendev.gemini.presentation.components.GeminiTopBar
import com.erendev.gemini.presentation.components.chat.ChatMessageTextField
import com.erendev.gemini.presentation.components.chat.ChatMessages
import com.erendev.gemini.presentation.components.menu.ChatMoreMenu
import com.erendev.gemini.presentation.navigation.LocalBackStack
import com.erendev.gemini.presentation.navigation.NavTarget
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.get
import org.koin.core.component.inject

object HomeScreen : BaseScreen<HomeViewModel>() {
    override fun getViewModel(): Lazy<HomeViewModel> = inject()


    @OptIn(
        ExperimentalResourceApi::class, ExperimentalMaterial3Api::class,
        ExperimentalComposeUiApi::class
    )
    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current
        val uiState by viewModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val chatText = remember {
            mutableStateOf<String?>(null)
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val recent by viewModel.recent.collectAsState()
        val lazyListState = rememberLazyListState()
        var showRenameDialog by remember {
            mutableStateOf(false)
        }
        var renameField by remember {
            mutableStateOf("")
        }
        LaunchedEffect(showRenameDialog) {
            if (showRenameDialog) {
                renameField = viewModel.getChat().title
            }
        }

        DismissibleNavigationDrawer(
            drawerContent = {
                GeminiDrawerSheet(
                    recent = recent,
                    drawerState = drawerState,
                    onNewChatClicked = {
                        viewModel.newChatClicked()
                    },
                    onChatClicked = { chatModel ->
                        viewModel.onChatClicked(chatModel)
                    },
                    onSearch = { searchText ->
                        viewModel.searchRecent(searchText)
                    },
                    onDrawerOpen = {
                        viewModel.getRecent()
                    },
                    onDrawerClosed = {
                    }
                )
            },
            drawerState = drawerState,
            content = {
                Scaffold(
                    topBar = {
                        GeminiTopBar(
                            title = Strings.APP_NAME,
                            actions = {
                                ChatMoreMenu(
                                    onViewDetailClicked = {
                                        keyboardController?.hide()
                                        backStack.push(NavTarget.ViewDetail)
                                    },
                                    onRenameClicked = {
                                        showRenameDialog = true
                                    },
                                    onDeleteClicked = {
                                        keyboardController?.hide()
                                        viewModel.onDeleteClicked()
                                    }
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    keyboardController?.hide()
                                    coroutineScope.launch {
                                        if (drawerState.isOpen) {
                                            drawerState.close()
                                        } else {
                                            drawerState.open()
                                        }
                                    }
                                }) {
                                    Icon(painterResource(Icons.Menu), null)
                                }
                            }
                        )
                    }
                ) {
                    Column(modifier = Modifier.padding(it)) {
                        ChatMessages(
                            messages = uiState.messages,
                            modifier = Modifier.weight(1f),
                            lazyListState = lazyListState,
                            onAnswering = uiState.onAnswering ?: false
                        )
                        ChatMessageTextField(
                            chatText = chatText,
                            onAnswering = uiState.onAnswering ?: false
                        ) { sendText ->
                            if (!sendText.isNullOrEmpty()) {
                                keyboardController?.hide()
                                viewModel.onSend(sendText)
                                chatText.value = ""
                            }
                        }
                    }
                }
            }
        )

        if (showRenameDialog) {
            AlertDialog(
                onDismissRequest = {
                    showRenameDialog = false
                },
                confirmButton = {
                    Button(onClick = {
                        if (renameField.isNotEmpty()) {
                            viewModel.renameChat(renameField)
                            showRenameDialog = false
                        }
                    }) {
                        Text(Strings.Button.Save)
                    }
                },
                text = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = renameField,
                            onValueChange = {
                                renameField = it
                            },
                            placeholder = {
                                Text(Strings.Chat.Title)
                            }
                        )
                    }
                },
                title = {
                    Text(Strings.Chat.RenameChat)
                }
            )
        }
    }
}