package com.verome.emotions.auth.presentation.registration

import com.verome.core.domain.empty
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : BaseViewModel(), RegistrationController {
    private val _uiState = MutableStateFlow(
        RegistrationUiState(email = String.empty, name = String.empty, password = String.empty),
    )
    internal val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    override fun onEmailFieldChange(email: String) {
        _uiState.tryToUpdate {
            it.copy(email = email, emailError = null)
        }
    }

    override fun onNameFieldChange(name: String) {
        _uiState.tryToUpdate {
            it.copy(name = name, nameError = null)
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

    override fun onRegisterButtonClick() {
        // TODO: Implement next screen navigation
    }

    override fun onLoginButtonClick() {
        sendEvent(
            OpenScreenEvent(
                Screen.Auth.LogIn,
            ),
        )
    }
}