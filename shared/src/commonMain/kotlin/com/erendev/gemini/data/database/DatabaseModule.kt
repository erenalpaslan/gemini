package com.erendev.gemini.data.database

import com.erendev.gemini.data.database.dao.ChatDao
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val databaseModule = module {
    factory { sqlDriverFactory() }
    single { createDatabase(driver = get()) }
    factoryOf(::ChatDao)
}