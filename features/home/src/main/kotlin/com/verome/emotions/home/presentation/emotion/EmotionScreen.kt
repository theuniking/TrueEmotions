package com.verome.emotions.home.presentation.emotion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.verome.emotions.home.presentation.emotion.content.EmotionContent

@Composable
fun EmotionScreen(viewModel: EmotionViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    EmotionContent(uiState = uiState, controller = viewModel)
}