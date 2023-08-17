package com.verome.emotions.presentation.main

import androidx.compose.runtime.Stable

@Stable
internal data class MainUiState(
    val isAuthorizedUser: Boolean,
    val isSplashVisible: Boolean,
)