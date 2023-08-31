package com.verome.emotions.home.presentation.emotion.content.common

import com.verome.core.domain.emotions.EmotionColor

internal sealed class EmotionScreens(val title: String) {
    data object NewEmotion : EmotionScreens(title = "New Emotion")
    data object ChooseEmotion : EmotionScreens(title = "Choose Emotion")
    class ChosenEmotion(val emotionColor: EmotionColor) : EmotionScreens(title = emotionColor.name)
}