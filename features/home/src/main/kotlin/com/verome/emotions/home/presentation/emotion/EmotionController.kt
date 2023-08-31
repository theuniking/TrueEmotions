package com.verome.emotions.home.presentation.emotion

import androidx.compose.runtime.Stable
import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.picker.date.DatePickerDialogData

@Stable
internal interface EmotionController {
    fun onContinueButtonClick()
    fun onBackClick()
    fun onActionFieldChange(action: String)
    fun onWhatHappenedFieldChange(whatHappened: String)
    fun onDateChangeClick()
    fun onTimeChangeClick()
    fun onEmotionClick(emotionColor: EmotionColor)
    fun onEmotionTagClick(index: Int)
    fun onDoneButtonClick()
    val datePickerControl: DialogControl<DatePickerDialogData, Long>
}