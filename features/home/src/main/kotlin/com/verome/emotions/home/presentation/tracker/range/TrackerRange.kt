package com.verome.emotions.home.presentation.tracker.range

enum class TrackerRange(val text: String, val average: String) {
    WEEK("Week", "Weekly"),
    MONTH("Month", "Monthly"), ;

    override fun toString() = text
}