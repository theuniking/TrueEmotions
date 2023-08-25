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
                    "Joy",
                )
            }
            is EmotionColor.Fear -> {
                listOf(
                    "Joy",
                )
            }
            is EmotionColor.Sadness -> {
                listOf(
                    "Joy",
                )
            }
            is EmotionColor.Shame -> {
                listOf(
                    "Joy",
                )
            }
        }
    }
}