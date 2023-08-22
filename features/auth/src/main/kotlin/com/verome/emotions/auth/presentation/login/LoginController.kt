package com.verome.emotions.auth.presentation.login

import androidx.compose.runtime.Stable

@Stable
internal interface LoginController {
    fun onEmailFieldChange(email: String)
    fun onPasswordFieldChange(password: String)
    fun changePasswordVisibility()
    fun onLoginButtonClick()
    fun onRegisterButtonClick()
}