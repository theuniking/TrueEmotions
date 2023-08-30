package com.verome.emotions.auth.domain.repository

interface LoginRepository {
    suspend fun loginUser(
        email: String,
        password: String,
    )
}