package com.verome.emotions.home.data.repository

import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.emotions.EmotionColor
import com.verome.emotions.home.domain.repository.EmotionsRepository

internal class DefaultEmotionsRepository : EmotionsRepository {
    override suspend fun getEmotions(): List<Emotion> {
        return listOf(
            Emotion(
                title = "Bro",
                tags = listOf(
                    "Bro",
                ),
                emotionColor = EmotionColor.Joy,
                emotions = listOf(
                    "Bro",
                ),
            ),
        )
    }

    override suspend fun insertEmotion(emotion: Emotion) {
        // todo: implement emotion insertion
    }
}