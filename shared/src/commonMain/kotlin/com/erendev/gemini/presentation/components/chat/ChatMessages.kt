package com.erendev.gemini.presentation.components.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erendev.gemini.common.resources.Icons
import comerendevgemini.Message
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatMessages(
    messages: List<Message> = emptyList(),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        when {
            messages.isEmpty() -> EmptyMessages()
            else -> MessageList(messages)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyMessages() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(Icons.Trash), null)
    }
}

@Composable
fun MessageList(
    messages: List<Message>
) {
    LazyColumn {
        items(messages) {
            Text(it.content)
            Divider()
        }
    }
}