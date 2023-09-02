package com.verome.emotions.home.data.repository

import com.verome.core.data.local.UserDao
import com.verome.core.data.local.preferences.PreferenceConstants
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.core.data.local.relations.UserEmotionCrossRef
import com.verome.core.data.mapper.toEmotion
import com.verome.core.data.mapper.toEmotionEntity
import com.verome.core.domain.emotions.Emotion
import com.verome.emotions.home.domain.repository.EmotionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class DefaultEmotionsRepository(
    private val dao: UserDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher,
) : EmotionsRepository {
    override suspend fun getEmotions(): List<Emotion> {
        val userId = preferenceManager.getLong(PreferenceConstants.KEY_USER_ID)
        if (userId == 0L) return emptyList()
        dao.getEmotionsOfUser(userId.toString())
        return dao.getEmotionsOfUser(userId.toString()).emotions.map { it.toEmotion() }
    }

    override suspend fun insertEmotion(emotion: Emotion) {
        withContext(dispatcher) {
            val userId = preferenceManager.getLong(PreferenceConstants.KEY_USER_ID)
            if (userId == 0L) return@withContext
            val emotionId = dao.saveEmotion(emotionEntity = emotion.toEmotionEntity(userId))
            dao.insertUserEmotionCrossRef(
                UserEmotionCrossRef(
                    userId = userId,
                    emotionId = emotionId,
                ),
            )
        }
    }
}