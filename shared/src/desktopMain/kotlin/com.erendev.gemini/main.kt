package com.erendev.gemini

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.bumble.appyx.navigation.integration.DesktopNodeHost
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.data.database.initializeJdbcSqlite
import com.erendev.gemini.presentation.navigation.RootNode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension
import java.awt.Toolkit


fun main() = application {
    GeminiDesktop()
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
fun ApplicationScope.GeminiDesktop() {
    val events: Channel<Unit> = Channel()
    val windowState = rememberWindowState(
        position = WindowPosition.Aligned(Alignment.Center),
        size = getPreferredWindowSize(720, 857)
    )
    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        initKoin()
        initializeJdbcSqlite()
        isInitialized = true
    }


    Window(
        onCloseRequest = ::exitApplication,
        title = Strings.APP_NAME,
        state = windowState,
        onKeyEvent = {
            false
        }
    ) {
        if (isInitialized) {
            DesktopNodeHost(
                windowState = windowState,
                onBackPressedEvents = events.receiveAsFlow()
            ) {
                RootNode(it)
            }
        }
    }
}

private fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
    val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
    val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
    val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
    return DpSize(width.dp, height.dp)
}