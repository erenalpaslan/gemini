package com.erendev.gemini.presentation.features.welcome

import androidx.compose.runtime.Composable
import com.bumble.appyx.components.backstack.operation.replace
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.presentation.navigation.LocalBackStack
import com.erendev.gemini.presentation.navigation.NavTarget
import org.koin.core.component.get

class WelcomeScreen : BaseScreen<WelcomeViewModel>() {
    override val viewModel: WelcomeViewModel
        get() = get()

    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current

        WelcomeContent {
            backStack.replace(NavTarget.Main)
        }
    }
}

