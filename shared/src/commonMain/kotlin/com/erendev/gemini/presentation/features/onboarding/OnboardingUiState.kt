package com.erendev.gemini.presentation.features.onboarding

import androidx.compose.ui.graphics.Color
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.theme.blue
import com.erendev.gemini.presentation.theme.blue_darkest
import com.erendev.gemini.presentation.theme.blue_lightest
import com.erendev.gemini.presentation.theme.green
import com.erendev.gemini.presentation.theme.green_light
import com.erendev.gemini.presentation.theme.ink_darker
import com.erendev.gemini.presentation.theme.red
import com.erendev.gemini.presentation.theme.red_lighter
import com.erendev.gemini.presentation.theme.red_lightest
import com.erendev.gemini.presentation.theme.sky
import com.erendev.gemini.presentation.theme.sky_dark
import com.erendev.gemini.presentation.theme.yellow
import com.erendev.gemini.presentation.theme.yellow_lightest

data class OnboardingUiState(
    val currentTheme: OnboardingTheme = OnboardingTheme.LETS_CREATE,
    val animate: Boolean = true
)

enum class OnboardingTheme(
    val title: String,
    val themeColor: Color,
    val backgroundColor: Color
) {
    LETS_CREATE(
        Strings.Onboarding.letsDo(Strings.Onboarding.CREATE),
        green,
        blue_darkest
    ),
    LETS_BRAINSTORM(
        Strings.Onboarding.letsDo(Strings.Onboarding.BRAINSTORM),
        red_lighter,
        yellow_lightest
    ),
    GEMINI(
        Strings.APP_NAME,
        yellow,
        red_lightest
    ),
    LETS_GO(
        Strings.Onboarding.letsDo(Strings.Onboarding.GO),
        red,
        sky_dark
    ),
    LETS_EXPLORE(
        Strings.Onboarding.letsDo(Strings.Onboarding.EXPLORE),
        sky,
        ink_darker
    ),
    LETS_DESIGN(
        Strings.Onboarding.letsDo(Strings.Onboarding.DESIGN),
        blue,
        green_light
    ),
    LETS_CHIT_CHAT(
        Strings.Onboarding.letsDo(Strings.Onboarding.CHIT_CHAT),
        green,
        blue_lightest
    )
}
