package com.verome.emotions.home.presentation.tracker

import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.CloseBottomSheetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor() : BaseViewModel(), TrackerController {
    private val _uiState = MutableStateFlow(
        TrackerUiState(),
    )
    internal val uiState: StateFlow<TrackerUiState> = _uiState.asStateFlow()

    init {
        initData()
    }

    override fun onDateRangeChanged() {
        TODO("Not yet implemented")
    }

    override fun onBackClick() {
        sendEvent(
            CloseBottomSheetEvent,
        )
    }

    private fun initData() {
        _uiState.tryToUpdate {
            it.copy(
                trackerValues = listOf(
                    5, 3, 10, 7, 0, 6,
                    5, 3, 10, 5,
                ),
            )
        }
    }
}