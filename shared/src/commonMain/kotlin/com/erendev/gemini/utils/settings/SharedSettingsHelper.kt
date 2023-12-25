package com.erendev.gemini.utils.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.typeOf

/**
 * The `SharedSettingsHelper` class provides a convenient interface for reading and storing
 * application settings securely in an encrypted database. It abstracts the underlying
 * operations for getting and storing string and boolean settings.
 *
 * @param encryptedSetting An instance of [Settings] used for reading and storing settings.
 */
class SharedSettingsHelper(
    val encryptedSetting: Settings
) {

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    inline fun <reified T> store(key: String, value: T) = encryptedSetting.encodeValue(serializer(typeOf<T>()), key, value)

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    inline fun <reified T> get(key: String, defaultValue: T? = null): T? = encryptedSetting.decodeValue(serializer(typeOf<T>()), key, defaultValue) as T?

    inline fun <reified T> getPrimitives(key: String, defaultValue: T) = when(T::class) {
        String::class -> encryptedSetting.get(key, defaultValue as String)
        Int::class -> encryptedSetting.get(key, defaultValue as Int)
        Float::class -> encryptedSetting.get(key, defaultValue as Float)
        Double::class -> encryptedSetting.get(key, defaultValue as Double)
        Long::class -> encryptedSetting.get(key, defaultValue as Long)
        Boolean::class -> encryptedSetting.get(key, defaultValue as Boolean)
        else -> throw IllegalArgumentException("Invalid type")
    } as T

    companion object {
        const val ENCRYPTED_DATABASE_NAME = "ENCRYPTED_SETTINGS"
    }
}

