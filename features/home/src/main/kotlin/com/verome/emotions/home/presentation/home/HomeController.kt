package com.verome.emotions.home.presentation.home

import androidx.compose.runtime.Stable

@Stable
internal interface HomeController {
    fun onActionCardMinuteOfReflectionClick()
    fun initEmotionHistory()
    fun onProfileClick()
    fun onNewEmotionClick()
}