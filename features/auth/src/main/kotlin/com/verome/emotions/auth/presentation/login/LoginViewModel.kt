package com.verome.emotions.auth.presentation.login

import com.verome.core.domain.empty
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel() : BaseViewModel(), LoginController {
    private val _uiState = MutableStateFlow(
        LoginUiState(email = String.empty, password = String.empty),
    )
    internal val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    override fun onEmailFieldChange(email: String) {
        _uiState.tryToUpdate {
            it.copy(email = email, emailError = null)
        }
    }

    override fun onPasswordFieldChange(password: String) {
        _uiState.tryToUpdate {
            it.copy(password = password, passwordError = null)
        }
    }

    override fun changePasswordVisibility() {
        _uiState.tryToUpdate {
            it.copy(isPasswordVisible = !uiState.value.isPasswordVisible)
        }
    }

    override fun onLoginButtonClick() {
        // TODO: Implement next screen navigation
    }

    override fun onRegisterButtonClick() {
        // TODO: Implement next screen navigation
    }
}