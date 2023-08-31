package com.verome.core.ui.widgets.dialog.picker.date

data class DatePickerDialogData(
    val selectedDate: Long? = null,
    val calendarConstraints: LongRange? = null,
)