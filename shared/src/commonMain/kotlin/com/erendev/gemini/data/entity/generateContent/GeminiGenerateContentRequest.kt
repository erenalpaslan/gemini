package com.erendev.gemini.data.entity.generateContent

import com.erendev.gemini.data.entity.GeminiContent
import kotlinx.serialization.Serializable

@Serializable
data class GeminiGenerateContentRequest(
    val contents: List<GeminiContent>
)
