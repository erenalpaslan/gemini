package com.erendev.gemini.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.erendev.gemini.AppDb

actual fun sqlDriverFactory(): SqlDriver? {
    return JdbcSqliteDriver("jdbc:sqlite:${DatabaseConstants.name}.db")
}

suspend fun initializeJdbcSqlite() {
    AppDatabase.appDb = AppDb(
        driver = JdbcSqliteDriver("${JdbcSqliteDriver.IN_MEMORY}${DatabaseConstants.name}.db")
            .also { AppDb.Schema.create(it).await() }
    )
}