package com.verome.core.domain.repository

import com.verome.core.domain.emotions.EmotionColor

interface EmotionsCategoryRepository {
    suspend fun getEmotionsFromCategory(
        emotion: EmotionColor,
    ): List<String>
}