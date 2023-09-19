package com.verome.emotions.home.presentation.tracker

import androidx.compose.runtime.Stable
import com.verome.emotions.home.presentation.tracker.range.TrackerRange
import java.time.LocalDate
import java.time.LocalDateTime

@Stable
internal interface TrackerController {
    fun onDateRangeChanged(range: TrackerRange)
    fun onBackClick()
    fun monthsToFloat(date: LocalDate): Float
    fun hoursToFloat(dateTime: LocalDateTime): Float
}