package com.erendev.gemini.utils

actual fun randomUUID(): String = Uuid.v4()


@JsModule("uuid")
@JsNonModule
private external object Uuid {
    fun v4(): String
}