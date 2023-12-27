package com.erendev.gemini.presentation.features.welcome

import com.erendev.gemini.common.BaseViewModel
import com.erendev.gemini.utils.settings.SettingKeys
import com.erendev.gemini.utils.settings.settings

class WelcomeViewModel: BaseViewModel() {

    fun onContinueClicked() {
        settings.store(SettingKeys.IS_FIRST_ENTER, false)
    }

}