package com.verome.emotions.home.presentation.emotion.content.common

import com.verome.core.domain.emotions.EmotionColor

internal sealed class EmotionScreens(val title: String) {
    data class AddEditEmotion(val isNewEmotion: Boolean = true) :
        EmotionScreens(title = if (isNewEmotion) "New Emotion" else "Edit Emotion")
    data object ChooseEmotion : EmotionScreens(title = "Choose Emotion")
    class ChosenEmotion(val emotionColor: EmotionColor) : EmotionScreens(title = emotionColor.name)
}