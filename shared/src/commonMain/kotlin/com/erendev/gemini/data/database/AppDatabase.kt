package com.erendev.gemini.data.database

import com.erendev.gemini.AppDb
import com.erendev.gemini.data.database.dao.ChatDao
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AppDatabase : KoinComponent {
    private val dispatchers: AppCoroutineDispatchers by inject()
    var appDb: AppDb? = null
    val chatDao: ChatDao = ChatDao(dispatchers)

    init {
        sqlDriverFactory()?.let {
            appDb = AppDb(
                driver = it
            )
        }
    }
}