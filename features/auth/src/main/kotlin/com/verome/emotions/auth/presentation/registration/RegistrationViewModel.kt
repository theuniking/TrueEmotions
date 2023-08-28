package com.verome.emotions.auth.presentation.registration

import com.verome.core.domain.empty
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.NavigateBackEvent
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.emotions.auth.domain.repository.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository,
) : BaseViewModel(), RegistrationController {
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
        val value = uiState.value

        // todo: implement data check
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
}