package com.verome.core.ui.external.app.service

import kotlinx.coroutines.flow.SharedFlow

interface UpdateService {
    val updateEvents: SharedFlow<Update>
    fun update(update: Update)
}