package com.verome.emotions.home.presentation.tracker

import androidx.compose.runtime.Stable

@Stable
internal data class TrackerUiState(
    val dateRange: Any? = null,
    val dailyAverage: Int? = null,
)
