package com.erendev.gemini.presentation.components.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumble.appyx.interactions.core.ui.property.impl.Width
import com.erendev.gemini.common.resources.Drawables
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.presentation.theme.black
import com.erendev.gemini.presentation.theme.green_light
import com.erendev.gemini.presentation.theme.green_lighter
import com.erendev.gemini.presentation.theme.green_lightest
import com.erendev.gemini.presentation.theme.ink_darkest
import com.erendev.gemini.presentation.theme.sky
import com.erendev.gemini.presentation.theme.sky_light
import comerendevgemini.Message
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatMessages(
    messages: List<Message> = emptyList(),
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    onAnswering: Boolean = false
) {
    Column(modifier = modifier) {
        when {
            messages.isEmpty() -> EmptyMessages()
            else -> MessageList(messages, lazyListState, onAnswering)
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
        Image(painterResource(Drawables.AppLogo), null, modifier = Modifier.fillMaxWidth(0.2f))
    }
}

@Composable
fun MessageList(
    messages: List<Message>,
    lazyListState: LazyListState,
    onAnswering: Boolean = false
) {
    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        lazyListState.animateScrollToItem(messages.lastIndex)
    }

    LaunchedEffect(onAnswering) {
        lazyListState.animateScrollToItem(messages.lastIndex)
        if (onAnswering) {
            while (true) {
                visible = true
                delay(1_000)
                visible = false
                delay(700)
            }
        }
    }

    LazyColumn(
        state = lazyListState
    ) {
        items(messages) {
            MessageItem(it)
        }
        if (onAnswering) {
            item {
                Box(modifier = Modifier.height(32.dp)) {
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(500)),
                        exit = fadeOut(tween(500)),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(sky_light))
                    }
                }
            }
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
) {
    Row(
        horizontalArrangement = if (message.isAiResponse) Arrangement.Start else Arrangement.End,
        modifier = Modifier.fillMaxWidth()
            .padding(
                start = if (message.isAiResponse) 16.dp else 64.dp,
                end = if (message.isAiResponse) 64.dp else 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        BoxWithConstraints (
            modifier = Modifier
                .wrapContentWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(if (message.isAiResponse) green_lighter else sky)
                .padding(8.dp)
        ) {
            SelectionContainer {
                Text(message.content, color = black)
            }
        }
    }
}
