package com.erendev.gemini

class JsPlatform: Platform {
    override val name: String = "Gemini Web/Js Application"
}

actual fun getPlatform(): Platform = JsPlatform()