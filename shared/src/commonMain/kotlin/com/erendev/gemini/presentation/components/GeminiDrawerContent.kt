package com.erendev.gemini.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GeminiDrawerSheet(
    onNewChatClicked: () -> Unit,
    onSearch: (String?) -> Unit
) {
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
                        Text("New Chat")
                    },
                    selected = false,
                    onClick = {
                        onNewChatClicked()
                    },
                    icon = {
                        Icon(painterResource(Icons.MessageEdit), null)
                    },
                    modifier = Modifier.padding(0.dp)
                )
            }
            items(listOf("Chat title 1", "Chat title 2", "Chat title 3")) {
                Text(it, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
        }
    }
}