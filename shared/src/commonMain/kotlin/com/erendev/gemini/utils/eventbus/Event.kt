package com.erendev.gemini.utils.eventbus

/**
 * Created by erenalpaslan on 10.10.2023.
 */
data class Event(
    val key: String,
    val data: Any? = null
)
