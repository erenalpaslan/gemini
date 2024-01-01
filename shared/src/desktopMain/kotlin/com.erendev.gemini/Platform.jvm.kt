package com.erendev.gemini

class JvmPlatform: Platform {
    override val name: String = "Gemini Desktop Application"
}

actual fun getPlatform(): Platform = JvmPlatform()