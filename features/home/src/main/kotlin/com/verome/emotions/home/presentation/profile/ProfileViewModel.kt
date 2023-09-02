package com.verome.emotions.home.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.viewModelScope
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.external.app.service.ExternalAppService
import com.verome.core.ui.external.app.service.Update
import com.verome.core.ui.external.app.service.UpdateService
import com.verome.core.ui.navigation.CloseBottomSheetEvent
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.input.InputDialogData
import com.verome.emotions.home.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository,
    private val externalAppService: ExternalAppService,
    private val updateService: UpdateService,
) : BaseViewModel(), ProfileController {
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(
        ProfileUiState.Loading,
    )
    internal val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    override val inputControl = DialogControl<InputDialogData, String>()

    init {
        handleGalleryEvents()
        handleUpdateEvents()
        updateUserData()
    }

    override fun onBackClick() {
        sendEvent(
            CloseBottomSheetEvent,
        )
    }

    override fun onLogOutButtonClick() {
        dataRequest(
            request = {
                repository.logOut()
            },
            onSuccess = {
                sendEvent(
                    OpenScreenEvent(
                        Screen.Auth.LogIn,
                    ),
                )
            },
        )
    }

    override fun onAvatarClick() {
        externalAppService.openGallery()
    }

    override fun onNameClick() {
        if (_uiState.value is ProfileUiState.Data) {
            dataRequest(
                request = {
                    inputControl.showForResult(
                        InputDialogData(
                            initialText = (uiState.value as ProfileUiState.Data).name,
                            fieldName = "name",
                        ),
                    )
                },
                onSuccess = { result ->
                    if (result == null) return@dataRequest
                    changeName(result)
                },
            )
        }
    }

    private fun handleUpdateEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            updateService.updateEvents.collect {
                if (it == Update.USER) {
                    updateUserData()
                }
            }
        }
    }

    private fun changeName(name: String) {
        dataRequest(
            request = {
                repository.changeName(name)
            },
            onSuccess = {
                updateService.update(Update.USER)
            },
        )
    }

    private fun updateUserData() {
        dataRequest(
            request = {
                repository.getCurrentUser()
            },
            onSuccess = { user ->
                _uiState.value = ProfileUiState.Data(
                    name = user.name,
                    email = user.email,
                    image = user.avatar?.let { avatar ->
                        getBitmapFromEncodedString(avatar)
                    },
                )
            },
        )
    }

    private fun handleGalleryEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            externalAppService.galleryEvents.collect {
                handleAvatar(it)
            }
        }
    }

    private fun handleAvatar(bitmap: Bitmap?) {
        bitmap?.let {
            dataRequest(
                request = {
                    repository.changeAvatar(encodeImage(bitmap))
                },
                onSuccess = {
                    updateUserData()
                },
            )
        }
    }

    private fun encodeImage(bitmap: Bitmap): String {
        val previewWidth = 150
        val previewHeight: Int = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun getBitmapFromEncodedString(encodedImage: String): Bitmap {
        val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}