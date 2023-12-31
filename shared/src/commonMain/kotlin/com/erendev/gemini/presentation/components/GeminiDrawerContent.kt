package com.erendev.gemini.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.data.entity.ChatModel
import com.erendev.gemini.presentation.features.home.HomeScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GeminiDrawerSheet(
    recent: List<ChatModel> = emptyList(),
    drawerState: DrawerState,
    onNewChatClicked: () -> Unit,
    onChatClicked: (ChatModel) -> Unit,
    onSearch: (String?) -> Unit,
    onDrawerOpen: () -> Unit = {},
    onDrawerClosed: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(drawerState.isOpen, drawerState.isClosed) {
        if (drawerState.isOpen) {
            keyboardController?.hide()
            onDrawerOpen()
        }

        if (drawerState.isClosed) {
            keyboardController?.hide()
            onDrawerClosed()
        }
    }

    DismissibleDrawerSheet {
        val searchText = remember {
            mutableStateOf<String?>(null)
        }

        GeminiBasicTextField(
            text = searchText,
            onValueChanged = {
                onSearch(it)
            },
            modifier = Modifier.padding(horizontal = 8.dp),
            placeholder = Strings.Chat.Search,
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            item {
                NavigationDrawerItem(
                    label = {
                        Text(Strings.Button.NewChat)
                    },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        onNewChatClicked()
                        keyboardController?.hide()
                    },
                    icon = {
                        Icon(painterResource(Icons.MessageEdit), null)
                    },
                    modifier = Modifier.padding(0.dp)
                )
            }
            items(recent) {
                NavigationDrawerItem(
                    label = {
                        Text(it.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        onChatClicked(it)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.padding(0.dp),
                )
            }
        }
    }
}