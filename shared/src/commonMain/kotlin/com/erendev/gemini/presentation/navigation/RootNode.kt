package com.erendev.gemini.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.AncestryInfo
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.appyx.utils.customisations.NodeCustomisationDirectoryImpl
import com.erendev.gemini.presentation.features.getstarted.GetStartedScreen
import com.erendev.gemini.presentation.features.splash.SplashScreen
import com.erendev.gemini.presentation.theme.GeminiTheme

/**
 * Created by erenalpaslan on 12.10.2023.
 */

val LocalBackStack = compositionLocalOf {
    BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.Splash,
            savedStateMap = mapOf()
        ),
        motionController = { BackStackFader(it) }
    )
}

val LocalBuildContext = compositionLocalOf {
    BuildContext(
        AncestryInfo.Root,
        mapOf(),
        NodeCustomisationDirectoryImpl()
    )
}

internal class RootNode(
    val buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.Splash,
            savedStateMap = buildContext.savedStateMap
        ),
        motionController = { BackStackFader(it) }
    )
): ParentNode<NavTarget>(
    buildContext = buildContext,
    appyxComponent = backStack
) {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun View(modifier: Modifier) {
        GeminiTheme {
            BottomSheetNavigator(
                sheetBackgroundColor = Color.Transparent
            ) {
                Column(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    CompositionLocalProvider(LocalBuildContext provides buildContext) {
                        CompositionLocalProvider(LocalBackStack provides backStack) {
                            AppyxComponent(
                                appyxComponent = backStack,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext) = when(interactionTarget) {
        NavTarget.Splash -> node(buildContext) { SplashScreen().Content() }
        NavTarget.OnBoarding -> node(buildContext) { GetStartedScreen().Content() }
        NavTarget.Main -> node(buildContext) {  }
        NavTarget.Chat -> node(buildContext) {  }
        NavTarget.Recent -> node(buildContext) {  }
    }

}