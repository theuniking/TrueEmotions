package com.verome.emotions.auth.domain.repository

interface RegistrationRepository {
    suspend fun registerUser(
        email: String,
        name: String,
        password: String,
    )
}