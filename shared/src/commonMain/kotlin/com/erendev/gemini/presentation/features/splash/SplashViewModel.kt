package com.erendev.gemini.presentation.features.splash

import com.erendev.gemini.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.erendev.gemini.utils.settings.SettingKeys
import com.erendev.gemini.utils.settings.settings

/**
 * Created by erenalpaslan on 30.09.2023
 */
class SplashViewModel : BaseViewModel() {

    private val _uiState: MutableStateFlow<SplashUiState> = MutableStateFlow(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        getIdToken()
    }


    private fun getIdToken() {
        viewModelScope.launch {
            val isFirstEnter = settings.getPrimitives<Boolean>(SettingKeys.IS_FIRST_ENTER, true)
            if (isFirstEnter) {
                _uiState.update { SplashUiState.NavigateToGetStarted }
            } else {
                _uiState.update { SplashUiState.NavigateToHome }
            }
        }
    }

}