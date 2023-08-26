package com.verome.emotions.home.presentation.home

import androidx.compose.runtime.Stable
import com.verome.core.domain.emotions.Emotion

@Stable
internal data class HomeUiState(
    val history: List<Emotion> = emptyList(),
)
