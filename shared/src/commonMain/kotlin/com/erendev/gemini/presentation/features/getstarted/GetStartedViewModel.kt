package com.erendev.gemini.presentation.features.getstarted

import com.erendev.gemini.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by erenalpaslan on 30.09.2023
 */
class GetStartedViewModel: BaseViewModel() {

    private val _uiState: MutableStateFlow<GetStartedUiState> = MutableStateFlow(GetStartedUiState.Initial)
    val uiState: StateFlow<GetStartedUiState> = _uiState.asStateFlow()

    /**
     * Handles the "Continue" button click action by updating the UI state to show the login view.
     */
    fun onContinueClicked() {
        _uiState.update {
            GetStartedUiState.Login
        }
    }

}