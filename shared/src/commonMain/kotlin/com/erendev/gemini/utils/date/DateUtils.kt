package com.erendev.gemini.utils.date

import kotlinx.datetime.Clock

object DateUtils {
    fun now() = Clock.System.now().toString()
}