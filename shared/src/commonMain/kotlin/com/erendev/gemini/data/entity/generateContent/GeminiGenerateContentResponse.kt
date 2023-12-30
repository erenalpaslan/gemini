package com.erendev.gemini.data.entity.generateContent

import com.erendev.gemini.data.entity.GeminiCandidate
import com.erendev.gemini.data.entity.PromptFeedback
import kotlinx.serialization.Serializable

@Serializable
data class GeminiGenerateContentResponse(
    val candidates: List<GeminiCandidate>,
    val promptFeedback: PromptFeedback
)

