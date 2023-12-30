package com.erendev.gemini.data.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.erendev.gemini.AppDb
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(
        schema = AppDb.Schema.synchronous(),
        name = "${DatabaseConstants.name}.db",
        onConfiguration = { config ->
            config.copy(
                extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
            )
        }
    )
}