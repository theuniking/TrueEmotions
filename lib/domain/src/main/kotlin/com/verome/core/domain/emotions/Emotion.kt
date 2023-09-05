package com.verome.core.domain.emotions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Emotion(
    val emotionId: Long? = null,
    val action: String,
    val whatHappened: String,
    val date: LocalDateTime,
    val tags: List<String>,
    val emotions: List<String>,
    val emotionColor: EmotionColor,
) : Parcelable {
    companion object {
        const val EMOTION_ID_WITHOUT_PARAMETER = "?emotionId="
        const val EMOTION_ID_PARAMETER = "$EMOTION_ID_WITHOUT_PARAMETER{emotionId}"
        const val EMOTION_ID_NAME = "emotionId"
        const val DEFAULT_FALSE_LONG = -1L
    }
}
