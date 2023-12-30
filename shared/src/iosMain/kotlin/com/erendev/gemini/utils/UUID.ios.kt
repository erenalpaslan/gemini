package com.erendev.gemini.utils

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID.UUID().UUIDString