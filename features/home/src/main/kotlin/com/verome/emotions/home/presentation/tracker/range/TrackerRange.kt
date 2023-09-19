package com.verome.emotions.home.presentation.tracker.range

enum class TrackerRange(val text: String, val average: String) {
    DAY("Day", "Dayly"),
    WEEK("Week", "Weekly"),
    MONTH("Month", "Monthly"),
    YEAR("Year", "Yearly"), ;

    override fun toString() = text
}