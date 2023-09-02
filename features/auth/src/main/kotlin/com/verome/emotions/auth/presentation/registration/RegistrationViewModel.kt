package com.verome.emotions.auth.presentation.registration

import com.verome.core.domain.empty
import com.verome.core.domain.error.handling.errorMessage
import com.verome.core.domain.repository.AuthValidation
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.NavigateBackEvent
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.emotions.auth.domain.repository.RegistrationRepository
import com.verome.emotions.auth.presentation.fields.AuthField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository,
    private val validation: AuthValidation,
) : BaseViewModel(), RegistrationController {
    private val _uiState = MutableStateFlow(
        RegistrationUiState(email = String.empty, name = String.empty, password = String.empty),
    )
    internal val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private var fieldJob: Job? = null

    override fun onEmailFieldChange(email: String) {
        _uiState.tryToUpdate {
            it.copy(email = email, emailError = null, isRegistrationEnabled = false)
        }
        validateField(data = email, field = AuthField.EMAIL)
    }

    override fun onNameFieldChange(name: String) {
        _uiState.tryToUpdate {
            it.copy(name = name, nameError = null, isRegistrationEnabled = false)
        }
        validateField(data = name, field = AuthField.NAME)
    }

    override fun onPasswordFieldChange(password: String) {
        _uiState.tryToUpdate {
            it.copy(password = password, passwordError = null, isRegistrationEnabled = false)
        }
        validateField(data = password, field = AuthField.PASSWORD)
    }

    override fun changePasswordVisibility() {
        _uiState.tryToUpdate {
            it.copy(isPasswordVisible = !uiState.value.isPasswordVisible)
        }
    }

    override fun onRegisterButtonClick() {
        val value = uiState.value
        dataRequest(
            request = {
                repository.registerUser(
                    email = value.email,
                    name = value.name,
                    password = value.password,
                )
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

    override fun onLoginButtonClick() {
        sendEvent(
            NavigateBackEvent,
        )
    }

    private fun validateField(data: String, field: AuthField) {
        fieldJob?.cancel()
        fieldJob = dataRequest(
            shouldUseBasicErrorHandler = false,
            request = {
                delay(500L)
                when (field) {
                    AuthField.EMAIL -> {
                        validation.validateEmailField(data)
                    }

                    AuthField.NAME -> {
                        validation.validateNameField(data)
                    }

                    AuthField.PASSWORD -> {
                        validation.validatePasswordField(data)
                    }
                }
            },
            onSuccess = {
                _uiState.tryToUpdate {
                    when (field) {
                        AuthField.EMAIL -> {
                            it.copy(emailError = null)
                        }

                        AuthField.NAME -> {
                            it.copy(nameError = null)
                        }

                        AuthField.PASSWORD -> {
                            it.copy(passwordError = null)
                        }
                    }.copy(
                        isRegistrationEnabled = (it.emailError == null && it.email.isNotEmpty()) && (it.passwordError == null && it.password.isNotEmpty()) && (it.nameError == null && it.name.isNotEmpty()),
                    )
                }
            },
            onError = { error ->
                _uiState.tryToUpdate {
                    when (field) {
                        AuthField.EMAIL -> it.copy(emailError = error.errorMessage)

                        AuthField.NAME -> it.copy(nameError = error.errorMessage)

                        AuthField.PASSWORD -> it.copy(passwordError = error.errorMessage)
                    }.copy(
                        isRegistrationEnabled = false,
                    )
                }
            },
        )
    }
}