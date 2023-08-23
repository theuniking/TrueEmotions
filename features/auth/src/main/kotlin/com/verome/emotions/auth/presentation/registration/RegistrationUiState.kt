package com.verome.emotions.auth.presentation.registration

import androidx.compose.runtime.Stable
import com.verome.core.domain.localization.string.VmRes

@Stable
internal data class RegistrationUiState(
    val email: String,
    val name: String,
    val password: String,
    val emailError: VmRes<CharSequence>? = null,
    val nameError: VmRes<CharSequence>? = null,
    val passwordError: VmRes<CharSequence>? = null,
    val isPasswordVisible: Boolean = false,
    val isRegistrationEnabled: Boolean = false,
)