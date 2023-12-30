package com.erendev.gemini.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PromptFeedback(
    val safetyRatings: List<SafetyRating>
)