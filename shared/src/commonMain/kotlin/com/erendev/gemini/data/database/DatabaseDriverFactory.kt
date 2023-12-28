package com.erendev.gemini.data.database

import app.cash.sqldelight.db.SqlDriver
import com.erendev.gemini.AppDb
import org.koin.core.scope.Scope

expect fun Scope.sqlDriverFactory(): SqlDriver
fun createDatabase(driver: SqlDriver): AppDb {
    return AppDb(
        driver = driver
    )
}

/*
val listOfMultimediaAdapter = Article.Adapter(object : ColumnAdapter<List<Multimedia>, String> {
    override fun decode(databaseValue: String): List<Multimedia> {
        return if (databaseValue.isEmpty())
            emptyList()
        else
            Json.decodeFromString<List<Multimedia>>(databaseValue)
    }

    override fun encode(value: List<Multimedia>): String {
        return Json.encodeToString(value)
    }
})*/