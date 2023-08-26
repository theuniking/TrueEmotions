package com.verome.emotions.home.presentation.home

import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.OpenScreenEvent
import com.verome.core.ui.navigation.Screen
import com.verome.emotions.home.domain.repository.EmotionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emotionsRepository: EmotionsRepository,
) : BaseViewModel(), HomeController {
    private val _uiState = MutableStateFlow(
        HomeUiState(),
    )
    internal val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        initEmotionCardHistory()
    }

    override fun onActionCardMinuteOfReflectionClicked() {
        sendEvent(
            OpenScreenEvent(
                Screen.Main.Reflection,
            ),
        )
    }

    override fun initEmotionCardHistory() {
        dataRequest(
            request = {
                emotionsRepository.getEmotions()
            },
            onSuccess = { result ->
                _uiState.tryToUpdate {
                    it.copy(
                        history = result,
                    )
                }
            },
        )
    }
}