package com.verome.emotions.auth.presentation.login

import com.verome.core.domain.empty
import com.verome.core.domain.error.handling.errorMessage
import com.verome.core.domain.repository.AuthValidation
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.emotions.auth.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val validation: AuthValidation,
) : BaseViewModel(), LoginController {
    private val _uiState = MutableStateFlow(
        LoginUiState(email = String.empty, password = String.empty),
    )
    internal val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var fieldJob: Job? = null

    override fun onEmailFieldChange(email: String) {
        _uiState.tryToUpdate {
            it.copy(email = email, emailError = null)
        }
        fieldJob?.cancel()
        fieldJob = dataRequest(
            shouldUseBasicErrorHandler = false,
            request = {
                delay(500L)
                validation.validateEmailField(email)
            },
            onSuccess = {
                _uiState.tryToUpdate {
                    it.copy(emailError = null).copy(
                        isLoginEnabled = (it.emailError == null && it.email.isNotEmpty()),
                    )
                }
            },
            onError = { error ->
                _uiState.tryToUpdate {
                    it.copy(emailError = error.errorMessage).copy(isLoginEnabled = false)
                }
            },
        )
    }

    override fun onPasswordFieldChange(password: String) {
        _uiState.tryToUpdate {
            it.copy(password = password)
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
            shouldUseBasicErrorHandler = false,
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
            onError = { error ->
                _uiState.tryToUpdate {
                    it.copy(
                        emailError = error.errorMessage,
                    )
                }
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