package com.verome.emotions.home.presentation.tracker

import androidx.compose.runtime.Stable

@Stable
internal interface TrackerController {
    fun onDateRangeChanged()
    fun onBackClick()
}