package com.verome.core.domain.emotions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Emotion(
    val title: String,
    val date: LocalDate = LocalDate.now(),
    val tags: List<String>,
    val emotions: List<String>,
    val emotionColor: EmotionColor,
) : Parcelable
