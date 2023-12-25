package com.erendev.gemini.presentation.features.getstarted

/**
 * Created by erenalpaslan on 6.10.2023.
 */
sealed interface GetStartedUiState {

    data object Initial: GetStartedUiState

    data object Login: GetStartedUiState


}