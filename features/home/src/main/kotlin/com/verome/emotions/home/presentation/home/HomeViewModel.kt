package com.verome.emotions.home.presentation.home

import androidx.lifecycle.viewModelScope
import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.emotions.Emotion.Companion.EMOTION_ID_WITHOUT_PARAMETER
import com.verome.core.domain.model.User
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.external.app.service.Update
import com.verome.core.ui.external.app.service.UpdateService
import com.verome.core.ui.navigation.OpenBottomSheetEvent
import com.verome.core.ui.navigation.OpenBottomSheetWithParametersEvent
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.emotions.home.domain.repository.EmotionsRepository
import com.verome.emotions.home.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emotionsRepository: EmotionsRepository,
    private val userRepository: UserRepository,
    private val updateService: UpdateService,
) : BaseViewModel(), HomeController {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Loading,
    )
    internal val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        updateUserData()
        updateEmotionHistory()
        handleUpdateEvents()
    }

    override fun onActionCardMinuteOfReflectionClick() {
        sendEvent(
            OpenScreenEvent(
                Screen.Main.Reflection,
            ),
        )
    }

    override fun onEmotionClick(emotionId: Long) {
        sendEvent(
            OpenBottomSheetWithParametersEvent(
                screen = Screen.BottomSheetScreen.AddEditEmotion,
                parameters = EMOTION_ID_WITHOUT_PARAMETER + emotionId,
            ),
        )
    }

    override fun onProfileClick() {
        sendEvent(
            OpenBottomSheetEvent(
                Screen.BottomSheetScreen.Profile,
            ),
        )
    }

    override fun onNewEmotionClick() {
        sendEvent(
            OpenBottomSheetEvent(
                Screen.BottomSheetScreen.AddEditEmotion,
            ),
        )
    }

    override fun onTrackerClick() {
        sendEvent(
            OpenBottomSheetEvent(
                Screen.BottomSheetScreen.Tracker,
            ),
        )
    }

    private fun updateEmotionHistory() {
        dataRequest(
            request = {
                emotionsRepository.getEmotions()
            },
            onSuccess = { history ->
                if (handleIsLoading(history = history)) return@dataRequest
                getUiStateData()?.let { data ->
                    _uiState.tryToUpdate {
                        data.copy(
                            history = history,
                        )
                    }
                }
            },
        )
    }

    private fun updateUserData() {
        dataRequest(
            request = {
                userRepository.getCurrentUser()
            },
            onSuccess = { user ->
                if (handleIsLoading(user = user)) return@dataRequest
                getUiStateData()?.let { data ->
                    _uiState.tryToUpdate {
                        data.copy(
                            name = user.name,
                        )
                    }
                }
            },
        )
    }

    private fun handleIsLoading(
        user: User? = null,
        history: List<Emotion>? = null,
    ): Boolean {
        if (_uiState.value !is HomeUiState.Loading) return false
        if (user != null) {
            _uiState.value = HomeUiState.Data(
                name = user.name,
            )
        }
        if (history != null) {
            _uiState.value = HomeUiState.Data(
                history = history,
            )
        }
        return true
    }

    private fun handleUpdateEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            updateService.updateEvents.collect {
                if (it == Update.USER) {
                    updateUserData()
                } else {
                    updateEmotionHistory()
                }
            }
        }
    }

    private fun getUiStateData(): HomeUiState.Data? {
        return uiState.value as? HomeUiState.Data
    }
}