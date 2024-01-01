package com.erendev.gemini.utils.settings

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class EncryptedSettingsHolder actual constructor() {
    private val delegate: Preferences = Preferences.userRoot()
    actual val encryptedSettings: Settings = PreferencesSettings(delegate)
}