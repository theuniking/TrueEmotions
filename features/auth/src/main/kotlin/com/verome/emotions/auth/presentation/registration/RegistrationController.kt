package com.verome.emotions.auth.presentation.registration

interface RegistrationController {
    fun onEmailFieldChange(email: String)
    fun onNameFieldChange(name: String)
    fun onPasswordFieldChange(password: String)
    fun changePasswordVisibility()
    fun onRegisterButtonClick()
    fun onLoginButtonClick()
}