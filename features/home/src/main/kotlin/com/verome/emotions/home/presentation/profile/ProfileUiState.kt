package com.verome.emotions.home.presentation.profile

import android.graphics.Bitmap
import androidx.compose.runtime.Stable

@Stable
internal sealed interface ProfileUiState {
    data object Loading : ProfileUiState

    data class Data(
        val name: String,
        val email: String,
        val image: Bitmap? = null,
    ) : ProfileUiState
}
