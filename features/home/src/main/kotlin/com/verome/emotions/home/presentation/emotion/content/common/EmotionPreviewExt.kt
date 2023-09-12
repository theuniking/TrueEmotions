package com.verome.emotions.home.presentation.emotion.content.common

import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.picker.date.DatePickerDialogData
import com.verome.core.ui.widgets.dialog.picker.time.TimePickerDialogData
import com.verome.emotions.home.presentation.emotion.EmotionController
import com.verome.emotions.home.presentation.emotion.EmotionUiState
import java.time.LocalDate
import java.time.LocalTime

internal object EmotionPreviewExt {
    val uiState: EmotionUiState.Data = EmotionUiState.Data(
        action = "Some stuff happened",
        whatHappened = "For example, this",
        tags = "place, event, etc",
        emotions = emptyList(),
        currentScreen = EmotionScreens.AddEditEmotion(),
        emotionColor = EmotionColor.Joy,
        date = LocalDate.now(),
        time = LocalTime.now(),
    )

    class FakeEmotionController : EmotionController {
        override fun onContinueButtonClick() = Unit
        override fun onBackClick() = Unit
        override fun onActionFieldChange(action: String) = Unit
        override fun onWhatHappenedFieldChange(whatHappened: String) = Unit
        override fun onTagsFieldChange(tags: String) = Unit
        override fun onDateChangeClick() = Unit
        override fun onTimeChangeClick() = Unit
        override fun onEmotionClick(emotionColor: EmotionColor) = Unit
        override fun onEmotionTagClick(index: Int) = Unit
        override fun onDoneButtonClick() = Unit
        override val datePickerControl = DialogControl<DatePickerDialogData, Long>()
        override val timePickerControl = DialogControl<TimePickerDialogData, TimePickerDialogData>()
    }
}