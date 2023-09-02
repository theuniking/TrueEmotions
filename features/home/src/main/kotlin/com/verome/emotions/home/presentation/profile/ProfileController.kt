package com.verome.emotions.home.presentation.profile

import androidx.compose.runtime.Stable
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.input.InputDialogData

@Stable
internal interface ProfileController {
    fun onBackClick()
    fun onLogOutButtonClick()
    fun onAvatarClick()
    fun onNameClick()
    val inputControl: DialogControl<InputDialogData, String>
}