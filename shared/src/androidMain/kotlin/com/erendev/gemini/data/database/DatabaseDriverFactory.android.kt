package com.erendev.gemini.data.database

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.erendev.gemini.AppDb
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(
        schema = AppDb.Schema,
        context = androidContext(),
        name = "${DatabaseConstants.name}.db",
        callback = object : AndroidSqliteDriver.Callback(AppDb.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
            }
        }
    )
}