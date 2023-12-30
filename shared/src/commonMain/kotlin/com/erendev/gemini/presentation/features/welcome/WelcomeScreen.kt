package com.erendev.gemini.presentation.features.welcome

import androidx.compose.runtime.Composable
import com.bumble.appyx.components.backstack.operation.replace
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.presentation.features.home.HomeViewModel
import com.erendev.gemini.presentation.navigation.LocalBackStack
import com.erendev.gemini.presentation.navigation.NavTarget
import org.koin.core.component.get
import org.koin.core.component.inject

class WelcomeScreen : BaseScreen<WelcomeViewModel>() {

    override fun getViewModel(): Lazy<WelcomeViewModel> = inject()

    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current

        WelcomeContent {
            viewModel.onContinueClicked()
            backStack.replace(NavTarget.Main)
        }
    }
}

