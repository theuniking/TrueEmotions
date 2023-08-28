package com.verome.emotions.auth.presentation.login

import com.verome.core.domain.empty
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.emotions.auth.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : BaseViewModel(), LoginController {
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
        val value = uiState.value

        dataRequest(
            request = {
                repository.loginUser(email = value.email, password = value.password)
            },
            onSuccess = {
                sendEvent(
                    OpenScreenEvent(
                        Screen.Main.Home,
                    ),
                )
            },
        )
    }

    override fun onRegisterButtonClick() {
        sendEvent(
            OpenScreenEvent(
                Screen.Auth.SignUp,
            ),
        )
    }
}