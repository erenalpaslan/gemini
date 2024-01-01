package com.erendev.gemini.data.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.async.coroutines.await
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlPreparedStatement
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import com.erendev.gemini.AppDb
import org.w3c.dom.Worker

actual fun sqlDriverFactory(): SqlDriver? {
    return null
}

suspend fun initializeWebWorker() {
    AppDatabase.appDb = AppDb(
        driver = WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        ).also { AppDb.Schema.create(it).await() }
    )
}