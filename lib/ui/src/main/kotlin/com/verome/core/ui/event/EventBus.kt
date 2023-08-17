package com.verome.core.ui.event

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBus {

    private val systemSharedFlow = MutableSharedFlow<SystemEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val navigationSharedFlow = MutableSharedFlow<SystemEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    // Используется во ViewModel
    val systemEvents = systemSharedFlow.asSharedFlow()

    // Используется в BaseScreen для навигации Voyager
    val navigationEvents = navigationSharedFlow.asSharedFlow()

    fun sendEvent(event: SystemEvent) {
        if (event is NavigationEvent) {
            navigationSharedFlow.tryEmit(event)
        } else {
            systemSharedFlow.tryEmit(event)
        }
    }

    fun sendEvents(events: List<SystemEvent>) {
        events.forEach {
            sendEvent(event = it)
        }
    }
}