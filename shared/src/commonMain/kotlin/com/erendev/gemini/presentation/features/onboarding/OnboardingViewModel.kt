package com.erendev.gemini.presentation.features.onboarding

import com.erendev.gemini.common.BaseViewModel
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform

/**
 * Created by erenalpaslan on 30.09.2023
 */
class OnboardingViewModel(
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<OnboardingUiState> =
        MutableStateFlow(OnboardingUiState())
    val uiState = _uiState.transform {
        while (true) {
            delay(2_000)
            emit(
                it.copy(
                    currentTheme = OnboardingTheme.entries[currentIndex % OnboardingTheme.entries.size],
                    animate = false
                )
            )
            delay(1_000)
            if (currentIndex == OnboardingTheme.entries.size)
                currentIndex = 0
            currentIndex += 1
            emit(
                it.copy(
                    currentTheme = OnboardingTheme.entries[currentIndex % OnboardingTheme.entries.size],
                    animate = true
                )
            )
        }
    }.flowOn(appCoroutineDispatchers.io)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OnboardingUiState())

    private var currentIndex: Int = 0
}