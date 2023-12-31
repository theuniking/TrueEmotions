package com.verome.emotions.auth.data.repository

import com.verome.core.data.local.UserDao
import com.verome.core.data.local.preferences.PreferenceConstants
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.core.domain.error.handling.NoSuchUser
import com.verome.core.domain.isNull
import com.verome.emotions.auth.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultLoginRepository @Inject constructor(
    private val dao: UserDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher,
) : LoginRepository {
    override suspend fun loginUser(email: String, password: String) {
        withContext(dispatcher) {
            val userId = dao.getIdFromEmailAndPassword(email, password)
            if (userId.isNull()) throw NoSuchUser(IllegalArgumentException())
            preferenceManager.putLong(
                PreferenceConstants.KEY_USER_ID,
                userId,
            )
        }
    }
}