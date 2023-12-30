package com.erendev.gemini.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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


    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current
        val uiState by viewModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        DismissibleNavigationDrawer(
            drawerContent = {
                GeminiDrawerSheet(
                    recent = uiState.recent,
                    drawerState = drawerState,
                    onNewChatClicked = {
                        viewModel.newChatClicked()
                    },
                    onChatClicked = { chatModel ->
                        viewModel.onChatClicked(chatModel)
                    },
                    onSearch = { _ ->
                        //TODO: Search on recents
                    },
                    onDrawerOpen = {
                        viewModel.getRecent()
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
                                        backStack.push(NavTarget.ViewDetail)
                                    },
                                    onRenameClicked = {

                                    },
                                    onDeleteClicked = {
                                        viewModel.onDeleteClicked()
                                    }
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
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
                        ChatMessages(uiState.messages, modifier = Modifier.weight(1f))
                        ChatMessageTextField { chatText ->
                            if (!chatText.isNullOrEmpty()) {
                                viewModel.onSend(chatText)
                            }
                        }
                    }
                }
            }
        )
    }
}