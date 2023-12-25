package com.erendev.gemini.utils.settings

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings

actual class EncryptedSettingsHolder actual constructor() {
    @OptIn(ExperimentalSettingsImplementation::class)
    actual val encryptedSettings: Settings = KeychainSettings(service = SharedSettingsHelper.ENCRYPTED_DATABASE_NAME)
}