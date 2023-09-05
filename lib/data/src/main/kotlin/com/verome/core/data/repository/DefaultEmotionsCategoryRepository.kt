package com.verome.core.data.repository

import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.domain.repository.EmotionsCategoryRepository

class DefaultEmotionsCategoryRepository : EmotionsCategoryRepository {
    override suspend fun getEmotionsFromCategory(emotion: EmotionColor): List<String> {
        return when (emotion) {
            is EmotionColor.Joy -> {
                listOf(
                    "Feeling of happiness",
                    "Fun",
                    "Enjoyment",
                    "Satisfaction",
                    "Peace",
                    "Love",
                    "Delight",
                    "Pleasure",
                    "Euphoria",
                    "Pride",
                    "Recognition",
                    "Friendliness",
                    "Trust",
                    "Kindness",
                    "Bliss",
                    "Sympathy",
                    "Enthusiasm",
                    "Admiration",
                    "Astonishment",
                    "Comfort",
                    "Harmony",
                )
            }
            is EmotionColor.Anger -> {
                listOf(
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                    "Long emotion name",
                    "Peace",
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                )
            }
            is EmotionColor.Fear -> {
                listOf(
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                    "Long emotion name",
                    "Peace",
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                )
            }
            is EmotionColor.Sadness -> {
                listOf(
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                    "Long emotion name",
                    "Peace",
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                )
            }
            is EmotionColor.Shame -> {
                listOf(
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                    "Long emotion name",
                    "Peace",
                    "Fun",
                    "Peace",
                    "Love",
                    "Delight",
                )
            }
            else -> {
                emptyList()
            }
        }
    }
}