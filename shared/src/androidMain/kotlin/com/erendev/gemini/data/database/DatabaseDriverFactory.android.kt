package com.erendev.gemini.data.database

import android.app.Application
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.erendev.gemini.AppDb
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.scope.Scope
import org.koin.mp.KoinPlatform

actual fun sqlDriverFactory(): SqlDriver? {
    val schema = AppDb.Schema.synchronous()
    return AndroidSqliteDriver(
        schema = schema,
        context = KoinPlatform.getKoin().get(),
        name = "${DatabaseConstants.name}.db",
        callback = object : AndroidSqliteDriver.Callback(schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
            }
        }
    )
}