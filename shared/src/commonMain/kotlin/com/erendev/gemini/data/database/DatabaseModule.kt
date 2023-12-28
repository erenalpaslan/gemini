package com.erendev.gemini.data.database

import org.koin.dsl.module

val databaseModule = module {
    factory { sqlDriverFactory() }
    single { createDatabase(driver = get()) }
}