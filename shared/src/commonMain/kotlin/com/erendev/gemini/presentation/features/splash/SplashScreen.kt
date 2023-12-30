package com.erendev.gemini.presentation.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.operation.replace
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.presentation.features.home.HomeViewModel
import com.erendev.gemini.presentation.navigation.LocalBackStack
import com.erendev.gemini.presentation.navigation.NavTarget
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.get
import org.koin.core.component.inject

/**
 * Created by erenalpaslan on 30.09.2023
 */

class SplashScreen() : BaseScreen<SplashViewModel>() {

    override fun getViewModel(): Lazy<SplashViewModel> = inject()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current
        val uiState by viewModel.uiState.collectAsState()

        when(uiState) {
            SplashUiState.Loading -> {}
            SplashUiState.NavigateToHome -> backStack.replace(NavTarget.Main)
            SplashUiState.NavigateToGetStarted -> backStack.replace(NavTarget.OnBoarding)
        }

        Scaffold {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(Icons.Default),
                    null,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
        }
    }

}