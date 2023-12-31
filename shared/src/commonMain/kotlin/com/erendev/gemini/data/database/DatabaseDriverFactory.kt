package com.erendev.gemini.data.database

import app.cash.sqldelight.db.SqlDriver
import com.erendev.gemini.AppDb
import org.koin.core.scope.Scope

expect fun sqlDriverFactory(): SqlDriver?