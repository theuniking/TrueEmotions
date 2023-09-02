package com.verome.core.data.mapper

import com.verome.core.data.local.model.EmotionEntity
import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.domain.empty

fun EmotionEntity.toEmotion(): Emotion {
    return Emotion(
        action = action,
        whatHappened = whatHappened,
        date = date,
        tags = tags,
        emotions = emotions,
        emotionColor = EmotionColor(String.empty, color = color),
    )
}
fun Emotion.toEmotionEntity(userId: Long): EmotionEntity {
    return EmotionEntity(
        action = action,
        whatHappened = whatHappened,
        date = date,
        tags = tags,
        emotions = emotions,
        color = emotionColor.color,
        userId = userId,
    )
}