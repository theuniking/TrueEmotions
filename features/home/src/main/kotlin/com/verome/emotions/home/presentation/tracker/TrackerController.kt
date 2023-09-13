package com.verome.emotions.home.presentation.tracker

import androidx.compose.runtime.Stable
import com.verome.emotions.home.presentation.tracker.range.TrackerRange

@Stable
internal interface TrackerController {
    fun onDateRangeChanged(range: TrackerRange)
    fun onBackClick()
}