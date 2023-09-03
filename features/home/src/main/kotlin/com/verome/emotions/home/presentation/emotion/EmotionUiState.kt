package com.verome.emotions.home.presentation.emotion

import androidx.compose.runtime.Stable
import com.verome.core.domain.emotions.EmotionColor
import com.verome.emotions.home.presentation.emotion.content.common.EmotionScreens
import java.time.LocalDateTime

@Stable
internal sealed interface EmotionUiState {
    data object Loading : EmotionUiState

    data class Data(
        val emotionId: Long? = null,
        val currentScreen: EmotionScreens = EmotionScreens.AddEditEmotion(),
        val action: String,
        val whatHappened: String,
        val tags: String,
        val date: LocalDateTime,
        val emotions: List<String>,
        val emotionColor: EmotionColor,
        val chosenEmotions: List<Int> = emptyList(),
    ) : EmotionUiState
}