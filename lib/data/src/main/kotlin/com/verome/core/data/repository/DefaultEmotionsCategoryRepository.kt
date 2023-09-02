package com.verome.core.data.repository

import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.domain.repository.EmotionsCategoryRepository

class DefaultEmotionsCategoryRepository : EmotionsCategoryRepository {
    override suspend fun getEmotionsFromCategory(emotion: EmotionColor): List<String> {
        return when (emotion) {
            is EmotionColor.Joy -> {
                listOf(
                    "Joy",
                )
            }
            is EmotionColor.Anger -> {
                listOf(
                    "Anger",
                )
            }
            is EmotionColor.Fear -> {
                listOf(
                    "Fear",
                )
            }
            is EmotionColor.Sadness -> {
                listOf(
                    "Sadness",
                )
            }
            is EmotionColor.Shame -> {
                listOf(
                    "Shame",
                )
            }
            else -> {
                emptyList()
            }
        }
    }
}