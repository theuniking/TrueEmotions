package com.verome.emotions.home.presentation.profile

import android.graphics.Bitmap
import androidx.compose.runtime.Stable

@Stable
internal data class ProfileUiState(
    val name: String? = null,
    val email: String? = null,
    val image: Bitmap? = null,
)