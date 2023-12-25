package com.erendev.gemini

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.bumble.appyx.navigation.integration.IosNodeHost
import com.bumble.appyx.navigation.integration.MainIntegrationPoint
import com.erendev.gemini.presentation.navigation.RootNode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

val backEvents: Channel<Unit> = Channel()
fun MainViewController() = ComposeUIViewController {
    IosNodeHost(
        modifier = Modifier,
        onBackPressedEvents = backEvents.receiveAsFlow(),
        integrationPoint = MainIntegrationPoint()
    ) {
        RootNode(buildContext = it)
    }
}