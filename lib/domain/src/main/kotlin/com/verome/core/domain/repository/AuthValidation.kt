package com.verome.core.domain.repository

interface AuthValidation {
    suspend fun validateNameField(name: String)
    suspend fun validateEmailField(email: String)
    suspend fun validatePasswordField(password: String)
}