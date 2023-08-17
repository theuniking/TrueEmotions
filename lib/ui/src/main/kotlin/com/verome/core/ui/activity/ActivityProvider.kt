package com.verome.core.ui.activity

import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class ActivityProvider {

    private val activityStateFlow = MutableStateFlow<ComponentActivity?>(null)

    val activity: ComponentActivity? get() = activityStateFlow.value

    fun attachActivity(activity: ComponentActivity) {
        activityStateFlow.value = activity
    }

    fun detachActivity() {
        activityStateFlow.value = null
    }

    suspend fun awaitActivity(): ComponentActivity {
        return activityStateFlow.filterNotNull().first()
    }
}