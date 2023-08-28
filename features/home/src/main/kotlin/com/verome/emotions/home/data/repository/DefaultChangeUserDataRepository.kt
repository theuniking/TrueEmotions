package com.verome.emotions.home.data.repository

import com.verome.core.data.local.UserDao
import com.verome.core.data.local.preferences.PreferenceConstants
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.core.data.mapper.toUser
import com.verome.core.domain.model.User
import com.verome.emotions.home.domain.repository.ChangeUserDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultChangeUserDataRepository @Inject constructor(
    private val dao: UserDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher,
) : ChangeUserDataRepository {
    override suspend fun getCurrentUser(): User {
        return dao.getUserById(preferenceManager.getLong(PreferenceConstants.KEY_USER_ID)).toUser()
    }

    override suspend fun changeAvatar(image: String) {
        withContext(dispatcher) {
            val userId =
                preferenceManager.getString(PreferenceConstants.KEY_USER_ID) ?: return@withContext
            dao.setAvatar(
                userId,
                image = image,
            )
        }
    }

    override suspend fun logOut() {
        withContext(dispatcher) {
            preferenceManager.clear()
        }
    }
}