package com.erendev.gemini.presentation.features.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.operation.replace
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.presentation.navigation.LocalBackStack
import com.erendev.gemini.presentation.navigation.NavTarget
import com.erendev.gemini.presentation.theme.sky_light
import org.koin.core.component.get

/**
 * Created by erenalpaslan on 30.09.2023
 */
class OnboardingScreen : BaseScreen<OnboardingViewModel>() {

    override val viewModel: OnboardingViewModel = get()

    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current
        val uiState by viewModel.uiState.collectAsState()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedOnboardingTheme(uiState.currentTheme, uiState.animate)
            Column(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(sky_light)
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
            ) {
                Text("Get Start")
                Text("ChatGemini is a KMP project that available on Mobile, Web and Desktop platforms")
                Button(
                    onClick = {
                        backStack.replace(NavTarget.Welcome)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get Start")
                }
            }
        }
    }
}