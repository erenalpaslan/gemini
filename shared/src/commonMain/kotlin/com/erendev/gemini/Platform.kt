package com.erendev.gemini

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform