package com.erendev.gemini.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.spotlight.Spotlight
import com.bumble.appyx.components.spotlight.SpotlightModel
import com.bumble.appyx.components.spotlight.operation.activate
import com.bumble.appyx.components.spotlight.operation.first
import com.bumble.appyx.components.spotlight.operation.last
import com.bumble.appyx.components.spotlight.ui.fader.SpotlightFader
import com.bumble.appyx.interactions.core.ui.Visualisation
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.erendev.gemini.presentation.components.MainOptionsBottomBar
import com.erendev.gemini.presentation.features.main.components.MainBottomOptions

/**
 * Created by erenalpaslan on 12.10.2023.
 */

val LocalTabNavigator = compositionLocalOf {
    Spotlight(
        model = SpotlightModel(
            items = listOf(MainBottomOptions.Home, MainBottomOptions.NewChat, MainBottomOptions.Recent),
            initialActiveIndex = 0f,
            savedStateMap = mapOf()
        ),
        visualisation = {
            SpotlightFader(it)
        }
    )
}

internal class MainRootNode(
    buildContext: BuildContext,
    private val spotlight: Spotlight<MainBottomOptions> = Spotlight(
        model = SpotlightModel(
            items = listOf(MainBottomOptions.Home, MainBottomOptions.NewChat, MainBottomOptions.Recent),
            initialActiveIndex = 0f,
            savedStateMap = buildContext.savedStateMap
        ),
        visualisation = { SpotlightFader(it) },
    )
): ParentNode<MainBottomOptions>(
    buildContext = buildContext,
    appyxComponent = spotlight
) {

    @Composable
    override fun View(modifier: Modifier) {
        val spotLightElement = spotlight.elements.collectAsState().value.onScreen.firstOrNull()?.interactionTarget

        CompositionLocalProvider(LocalTabNavigator provides spotlight) {
            Scaffold(
                bottomBar = {
                    MainOptionsBottomBar(
                        selectedIndex = spotLightElement?.id ?: 0,
                    ) { tab ->
                        when ((tab as MainBottomOptions)) {
                            MainBottomOptions.Home -> {
                                spotlight.first()
                            }

                            MainBottomOptions.NewChat -> {
                                spotlight.activate(1f)
                            }

                            MainBottomOptions.Recent -> {
                                spotlight.last()
                            }
                        }
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    Scaffold { _ ->
                        AppyxComponent(
                            appyxComponent = spotlight,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }

    override fun resolve(interactionTarget: MainBottomOptions, buildContext: BuildContext): Node = when(interactionTarget) {
        MainBottomOptions.Home -> node(buildContext) {  }
        MainBottomOptions.NewChat -> node(buildContext) {  }
        MainBottomOptions.Recent -> node(buildContext) {  }
    }


}