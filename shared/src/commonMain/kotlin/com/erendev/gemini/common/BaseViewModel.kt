package com.erendev.gemini.common

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by erenalpaslan on 30.09.2023
 */
abstract class BaseViewModel(): ScreenModel {

    val viewModelScope: CoroutineScope = coroutineScope

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _showProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showProgressIndicator: StateFlow<Boolean> = _showProgress.asStateFlow()

    override fun onDispose() {
        onDestroy()
        super.onDispose()
    }

    open fun onDestroy() {
    }

    fun removeError() {
        _error.value = null
    }

    fun showProgress() {
        _showProgress.value = true
    }

    fun hideProgress() {
        _showProgress.value = false
    }

    fun showError(message: String?) {
        _error.update { message }
    }
}