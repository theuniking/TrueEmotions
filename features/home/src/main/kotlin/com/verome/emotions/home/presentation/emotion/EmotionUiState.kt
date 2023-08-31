package com.verome.emotions.home.presentation.emotion

import androidx.compose.runtime.Stable
import com.verome.emotions.home.presentation.emotion.content.common.EmotionScreens

@Stable
internal sealed interface EmotionUiState {
    data object Loading : EmotionUiState

    data class Data(
        val currentScreen: EmotionScreens,
        val action: String,
        val whatHappened: String,
        val date: String,
        val emotions: List<String>,
        val chosenEmotions: List<Int> = emptyList(),
    ) : EmotionUiState
}