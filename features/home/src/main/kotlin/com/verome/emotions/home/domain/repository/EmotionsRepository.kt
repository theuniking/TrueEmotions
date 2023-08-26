package com.verome.emotions.home.domain.repository

import com.verome.core.domain.emotions.Emotion

interface EmotionsRepository {
    suspend fun getEmotions(): List<Emotion>
    suspend fun insertEmotion(emotion: Emotion)
}