package com.erendev.gemini.utils.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual class EncryptedSettingsHolder actual constructor() {
    actual val encryptedSettings: Settings = StorageSettings()
}