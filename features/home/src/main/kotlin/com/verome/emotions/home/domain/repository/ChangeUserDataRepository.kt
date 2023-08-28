package com.verome.emotions.home.domain.repository

import com.verome.core.domain.model.User

interface ChangeUserDataRepository {
    suspend fun getCurrentUser(): User
    suspend fun changeAvatar(image: String)
    suspend fun logOut()
}