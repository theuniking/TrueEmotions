package com.verome.emotions.home.presentation.tracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TrackerScreen(viewModel: TrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    TrackerContent(uiState = uiState, controller = viewModel)
}