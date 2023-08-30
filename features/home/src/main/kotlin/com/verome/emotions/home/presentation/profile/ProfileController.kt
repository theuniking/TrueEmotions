package com.verome.emotions.home.presentation.profile

import androidx.compose.runtime.Stable

@Stable
internal interface ProfileController {
    fun onBackClicked()
    fun onLogOutButtonClick()
    fun onAvatarClick()
}