package com.verome.emotions.home.data.repository

import com.verome.core.data.local.UserDao
import com.verome.core.data.local.preferences.PreferenceConstants
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.core.data.mapper.toUser
import com.verome.core.domain.model.User
import com.verome.emotions.home.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultUserRepository @Inject constructor(
    private val dao: UserDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun getCurrentUser(): User {
        return dao.getUserById(preferenceManager.getLong(PreferenceConstants.KEY_USER_ID)).toUser()
    }

    override suspend fun changeAvatar(image: String) {
        withContext(dispatcher) {
            val userId = preferenceManager.getLong(PreferenceConstants.KEY_USER_ID)
            if (userId == 0L) return@withContext
            dao.setAvatar(
                userId.toString(),
                image = image,
            )
        }
    }

    override suspend fun changeName(name: String) {
        withContext(dispatcher) {
            val userId = preferenceManager.getLong(PreferenceConstants.KEY_USER_ID)
            if (userId == 0L) return@withContext
            dao.setName(
                userId.toString(),
                name = name,
            )
        }
    }

    override suspend fun logOut() {
        withContext(dispatcher) {
            preferenceManager.clear()
        }
    }
}