package com.verome.emotions.home.domain.repository

import com.verome.core.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User
    suspend fun changeAvatar(image: String)
    suspend fun changeName(name: String)
    suspend fun logOut()
}