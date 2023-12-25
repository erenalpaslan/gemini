package com.erendev.gemini.utils.settings

/**
 * Created by erenalpaslan on 30.09.2023
 */
actual val settings: SharedSettingsHelper = SharedSettingsHelper(EncryptedSettingsHolder().encryptedSettings)