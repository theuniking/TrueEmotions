package com.verome.emotions.auth.data.repository

import com.verome.core.data.local.UserDao
import com.verome.core.data.local.model.UserEntity
import com.verome.core.data.local.preferences.PreferenceConstants
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.emotions.auth.domain.repository.RegistrationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultRegistrationRepository @Inject constructor(
    private val dao: UserDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher,
) : RegistrationRepository {
    override suspend fun registerUser(email: String, name: String, password: String) {
        withContext(dispatcher) {
            preferenceManager.putLong(
                PreferenceConstants.KEY_USER_ID,
                dao.saveUser(
                    UserEntity(
                        email = email,
                        name = name,
                        password = password,
                    ),
                ),
            )
        }
    }
}