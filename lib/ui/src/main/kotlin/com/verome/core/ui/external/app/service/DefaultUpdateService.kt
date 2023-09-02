package com.verome.core.ui.external.app.service

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DefaultUpdateService : UpdateService {
    private val updateChannel = MutableSharedFlow<Update>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    override val updateEvents = updateChannel.asSharedFlow()

    override fun update(update: Update) {
        updateChannel.tryEmit(update)
    }
}