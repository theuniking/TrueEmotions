package com.verome.emotions.home.presentation.home

import androidx.compose.runtime.Stable
import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.empty

@Stable
internal sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Data(
        val name: String = String.empty,
        val history: List<Emotion> = emptyList(),
    ) : HomeUiState
}