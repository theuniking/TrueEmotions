package com.verome.emotions.home.presentation.emotion.content.common

import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.picker.date.DatePickerDialogData
import com.verome.emotions.home.presentation.emotion.EmotionController
import com.verome.emotions.home.presentation.emotion.EmotionUiState

internal object EmotionPreviewExt {
    val uiState: EmotionUiState.Data = EmotionUiState.Data(
        action = "Some stuff happened",
        whatHappened = "For example, this",
        emotions = emptyList(),
        currentScreen = EmotionScreens.NewEmotion,
        date = "16 aug",
    )

    class FakeEmotionController : EmotionController {
        override fun onContinueButtonClick() = Unit
        override fun onBackClick() = Unit
        override fun onActionFieldChange(action: String) = Unit
        override fun onWhatHappenedFieldChange(whatHappened: String) = Unit
        override fun onDateChangeClick() = Unit
        override fun onTimeChangeClick() = Unit
        override fun onEmotionClick(emotionColor: EmotionColor) = Unit
        override fun onEmotionTagClick(index: Int) = Unit
        override fun onDoneButtonClick() = Unit
        override val datePickerControl = DialogControl<DatePickerDialogData, Long>()
    }
}