package com.verome.emotions.auth.presentation.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    RegistrationContent(uiState = uiState, controller = viewModel)
}
