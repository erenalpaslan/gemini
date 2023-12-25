package com.erendev.gemini.presentation.features.splash

/**
 * Created by erenalpaslan on 30.09.2023
 */
sealed interface SplashUiState {

    data object NavigateToHome: SplashUiState

    data object NavigateToGetStarted: SplashUiState

    data object Loading: SplashUiState

}