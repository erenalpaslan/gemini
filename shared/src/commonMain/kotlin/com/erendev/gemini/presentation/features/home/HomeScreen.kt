package com.erendev.gemini.presentation.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.components.GeminiDrawerSheet
import com.erendev.gemini.presentation.components.GeminiTopBar
import com.erendev.gemini.presentation.components.chat.ChatMessageTextField
import com.erendev.gemini.presentation.components.menu.ChatMoreMenu
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.get

object HomeScreen : BaseScreen<HomeViewModel>() {
    override val viewModel: HomeViewModel
        get() = get()

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Screen() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        DismissibleNavigationDrawer(
            drawerContent = {
                GeminiDrawerSheet(
                    onNewChatClicked = {},
                    onRecentClicked = {}
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

                                    },
                                    onRenameClicked = {

                                    },
                                    onDeleteClicked = {

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
                        Box(modifier = Modifier.fillMaxSize().weight(1f))
                        ChatMessageTextField { chatText ->
                            viewModel.onSend(chatText)
                        }
                    }
                }
            }
        )
    }
}