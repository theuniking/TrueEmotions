package com.verome.core.domain.repository

interface SessionManager {

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun setTokens(accessToken: String?, refreshToken: String?)

    suspend fun logOut()
}