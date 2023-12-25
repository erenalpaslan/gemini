package com.erendev.gemini.utils.settings

import com.russhwolf.settings.Settings

expect class EncryptedSettingsHolder() {
    val encryptedSettings: Settings
}