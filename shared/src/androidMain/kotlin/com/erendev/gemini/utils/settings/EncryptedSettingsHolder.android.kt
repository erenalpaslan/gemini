package com.erendev.gemini.utils.settings

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual class EncryptedSettingsHolder actual constructor(): KoinComponent {

    private val delegate: SharedPreferences = EncryptedSharedPreferences.create(
        get(),
        SharedSettingsHelper.ENCRYPTED_DATABASE_NAME,
        MasterKey.Builder(get())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual val encryptedSettings = SharedPreferencesSettings(delegate) as Settings
}