package com.verome.emotions.presentation.main

import androidx.compose.runtime.Composable
import com.verome.core.ui.widgets.dialog.alert.ShowAlertDialog

@Composable
internal fun MainContent(uiState: MainUiState, viewModel: MainViewModel) {
    // todo: Add NavHost

    ShowAlertDialog(dialogControl = viewModel.messageDialogControl)
}