package com.erendev.gemini.utils.eventbus

import co.touchlab.kermit.Logger
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlin.coroutines.coroutineContext

/**
 * Created by erenalpaslan on 10.10.2023.
 */
/**
 * The `EventBus` object facilitates communication between different components of the application
 * by allowing them to publish and subscribe to events.
 */
object EventBus {
    const val TAG = "EventBus"
    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    /**
     * Publishes an event to the event bus.
     *
     * @param event The event to be published.
     */
    suspend fun publish(event: Event) {
        Logger.d(tag = TAG, null) { "=================================================" }
        Logger.d(tag = TAG, null) { "Event published: $event" }
        Logger.d(tag = TAG, null) { "=================================================" }
        _events.emit(event)
    }

    /**
     * Subscribes to one or more events specified by their keys and invokes the provided [onEvent]
     * lambda when an event matching the keys is emitted.
     *
     * @param keys A vararg array of event keys to subscribe to.
     * @param onEvent A lambda function to be invoked when a matching event is emitted.
     */
    suspend inline fun subscribe(vararg keys: String, crossinline onEvent: (Event) -> Unit) {
        events.filter { keys.contains(it.key) }
            .collectLatest { event ->
                coroutineContext.ensureActive()
                Logger.d(tag = TAG, null) { "=================================================" }
                Logger.d(tag = TAG, null) { "Event observed: $event" }
                Logger.d(tag = TAG, null) { "=================================================" }
                onEvent(event)
            }
    }

    suspend inline fun subscribe(key: String, crossinline onEvent: (Event) -> Unit) {
        events.filter { key == it.key }
            .collectLatest { event ->
                coroutineContext.ensureActive()
                Logger.d(tag = TAG, null) { "=================================================" }
                Logger.d(tag = TAG, null) { "Event observed: $event" }
                Logger.d(tag = TAG, null) { "=================================================" }
                onEvent(event)
            }
    }
}

