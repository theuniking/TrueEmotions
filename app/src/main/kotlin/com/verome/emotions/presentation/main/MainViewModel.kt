package com.verome.emotions.presentation.main

import androidx.lifecycle.viewModelScope
import com.verome.core.data.local.preferences.PreferenceConstants
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.core.domain.isNull
import com.verome.core.domain.message.Message
import com.verome.core.domain.message.MessageHandler
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.DialogResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val messageHandler: MessageHandler,
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        MainUiState(
            isAuthorizedUser = false,
            isSplashVisible = true,
        ),
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    val messageDialogControl = DialogControl<Message, DialogResult>()

    init {
        authUser()
    }

    fun collectMessages() {
        viewModelScope.launch {
            messageHandler.messageFlow.collect { message ->
                val result = messageDialogControl.showForResult(message) ?: DialogResult.Cancel

                val messageAction = message.positiveButtonAction
                if (result == DialogResult.Confirm && messageAction != null) {
                    messageAction()
                }
            }
        }
    }

    private fun authUser() {
        dataRequest(
            request = {
                !preferenceManager.getLong(PreferenceConstants.KEY_USER_ID).isNull()
            },
            onSuccess = { isSignedIn ->
                _uiState.tryToUpdate {
                    it.copy(
                        isAuthorizedUser = isSignedIn,
                        isSplashVisible = false,
                    )
                }
            },
        )
    }
}