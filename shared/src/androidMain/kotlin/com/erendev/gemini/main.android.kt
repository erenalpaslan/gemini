package com.erendev.gemini

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.bumble.appyx.navigation.integration.NodeActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import com.erendev.gemini.presentation.navigation.RootNode

@Composable fun MainView() {
    val activity = LocalContext.current as NodeActivity
    NodeHost(
        lifecycle = AndroidLifecycle(LocalLifecycleOwner.current.lifecycle),
        integrationPoint = activity.appyxV2IntegrationPoint
    ) {
        RootNode(it)
    }
}
