package com.verome.emotions.auth.presentation.login

import androidx.compose.runtime.Stable
import com.verome.core.domain.localization.string.VmRes

@Stable
internal data class LoginUiState(
    val email: String,
    val password: String,
    val emailError: VmRes<CharSequence>? = null,
    val passwordError: VmRes<CharSequence>? = null,
    val isPasswordVisible: Boolean = false,
    val isLoginEnabled: Boolean = false,
)
