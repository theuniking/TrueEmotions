package com.verome.core.domain.emotions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Emotion(
    val action: String,
    val whatHappened: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val tags: List<String>,
    val emotions: List<String>,
    val emotionColor: EmotionColor,
) : Parcelable
