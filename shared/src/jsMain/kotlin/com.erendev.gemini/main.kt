package com.erendev.gemini

import BrowserViewportWindow
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.bumble.appyx.navigation.integration.MainIntegrationPoint
import com.bumble.appyx.navigation.integration.NodeFactory
import com.bumble.appyx.navigation.integration.ScreenSize
import com.bumble.appyx.navigation.integration.WebNodeHost
import com.erendev.gemini.data.database.initializeWebWorker
import com.erendev.gemini.presentation.features.home.HomeScreen
import com.erendev.gemini.presentation.navigation.RootNode
import com.erendev.gemini.presentation.theme.GeminiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val events: Channel<Unit> = Channel()
    onWasmReady {
        CanvasBasedWindow(
            title = "Gemini",
            canvasElementId = "ComposeTarget"
        ) {
            var isInitialized by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                initKoin()
                initializeWebWorker()
                isInitialized = true
            }
            val requester = remember { FocusRequester() }
            var hasFocus by remember { mutableStateOf(false) }
            var screenSize by remember { mutableStateOf(ScreenSize(0.dp, 0.dp)) }
            val eventScope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

            GeminiTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { screenSize = ScreenSize(it.width.dp, it.height.dp) }
                        .onKeyEvent {
                            // See back handling section in the docs below!
                            onKeyEvent(it, events, eventScope)
                            return@onKeyEvent true
                        }
                        .focusRequester(requester)
                        .focusable()
                        .onFocusChanged { hasFocus = it.hasFocus }
                ) {
                    if (isInitialized) {
                        HomeScreen.Content()
                    }
                }

                if (!hasFocus) {
                    LaunchedEffect(Unit) {
                        requester.requestFocus()
                    }
                }
            }

        }
    }
}

private fun onKeyEvent(
    keyEvent: KeyEvent,
    events: Channel<Unit>,
    coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
): Boolean =
    when {
        // You can also register e.g. Key.Escape instead of BackSpace:
        keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace -> {
            coroutineScope.launch { events.send(Unit) }
            true
        }

        else -> false
    }