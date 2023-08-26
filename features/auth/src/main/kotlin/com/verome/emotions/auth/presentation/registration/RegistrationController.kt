package com.verome.emotions.auth.presentation.registration

import androidx.compose.runtime.Stable

@Stable
internal interface RegistrationController {
    fun onEmailFieldChange(email: String)
    fun onNameFieldChange(name: String)
    fun onPasswordFieldChange(password: String)
    fun changePasswordVisibility()
    fun onRegisterButtonClick()
    fun onLoginButtonClick()
}