package com.verome.emotions.home.presentation.emotion

import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.domain.empty
import com.verome.core.domain.repository.EmotionsCategoryRepository
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.CloseBottomSheetEvent
import com.verome.core.ui.navigation.NavigateBackEvent
import com.verome.core.ui.utils.getMillis
import com.verome.core.ui.utils.toTimeDateString
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.picker.date.DatePickerDialogData
import com.verome.emotions.home.domain.repository.EmotionsRepository
import com.verome.emotions.home.presentation.emotion.content.common.EmotionScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

private const val MAX_ACTION_CHARS = 100
private const val MAX_TAGS_MINUS_ONE = 4
private const val ONE_DAY = 1L

@HiltViewModel
class EmotionViewModel @Inject constructor(
    private val emotionsCategoryRepository: EmotionsCategoryRepository,
    private val emotionsRepository: EmotionsRepository,
) : BaseViewModel(), EmotionController {
    private val _uiState: MutableStateFlow<EmotionUiState> = MutableStateFlow(
        EmotionUiState.Loading,
    )
    internal val uiState: StateFlow<EmotionUiState> = _uiState.asStateFlow()
    private val date: MutableStateFlow<Long> = MutableStateFlow(LocalDate.now().getMillis())
    override val datePickerControl = DialogControl<DatePickerDialogData, Long>()

    init {
        initData()
    }

    override fun onContinueButtonClick() {
        getUiStateData()?.let { data ->
            _uiState.tryToUpdate {
                data.copy(
                    currentScreen = EmotionScreens.ChooseEmotion,
                )
            }
        }
    }

    override fun onBackClick() {
        getUiStateData()?.let { data ->
            when (data.currentScreen) {
                is EmotionScreens.NewEmotion -> {
                    sendEvent(
                        NavigateBackEvent,
                    )
                }

                is EmotionScreens.ChooseEmotion -> {
                    _uiState.tryToUpdate {
                        data.copy(
                            currentScreen = EmotionScreens.NewEmotion,
                        )
                    }
                }

                is EmotionScreens.ChosenEmotion -> {
                    _uiState.tryToUpdate {
                        data.copy(
                            currentScreen = EmotionScreens.ChooseEmotion,
                            chosenEmotions = emptyList(),
                        )
                    }
                }
            }
        }
    }

    override fun onActionFieldChange(action: String) {
        if (action.length > MAX_ACTION_CHARS) return
        getUiStateData()?.let { data ->
            _uiState.tryToUpdate {
                data.copy(
                    action = action,
                )
            }
        }
    }

    override fun onWhatHappenedFieldChange(whatHappened: String) {
        getUiStateData()?.let { data ->
            _uiState.tryToUpdate {
                data.copy(
                    whatHappened = whatHappened,
                )
            }
        }
    }

    override fun onDateChangeClick() {
        dataRequest(
            request = {
                datePickerControl.showForResult(
                    DatePickerDialogData(
                        selectedDate = date.value,
                        calendarConstraints = LongRange(
                            start = 0L,
                            endInclusive = LocalDate.now().plusDays(ONE_DAY).getMillis(),
                        ),
                    ),
                )
            },
            onSuccess = { selectedDate ->
                selectedDate?.let {
                    date.update { selectedDate }
                    getUiStateData()?.let { data ->
                        _uiState.tryToUpdate {
                            data.copy(
                                date = selectedDate.toTimeDateString(),
                            )
                        }
                    }
                }
            },
        )
    }

    override fun onTimeChangeClick() {
        TODO("Not yet implemented")
    }

    override fun onEmotionClick(emotionColor: EmotionColor) {
        dataRequest(
            request = {
                emotionsCategoryRepository.getEmotionsFromCategory(emotionColor)
            },
            onSuccess = { emotions ->
                getUiStateData()?.let { data ->
                    _uiState.tryToUpdate {
                        data.copy(
                            emotions = emotions,
                            currentScreen = EmotionScreens.ChosenEmotion(emotionColor = emotionColor),
                        )
                    }
                }
            },
        )
    }

    override fun onEmotionTagClick(index: Int) {
        getUiStateData()?.let { data ->
            _uiState.tryToUpdate {
                data.copy(
                    chosenEmotions = data.chosenEmotions.toMutableList().let { newList ->
                        if (
                            !newList.contains(index) &&
                            data.chosenEmotions.size <= MAX_TAGS_MINUS_ONE
                        ) {
                            newList.add(index)
                        } else {
                            newList.remove(index)
                        }
                        return@let newList
                    },
                )
            }
        }
    }

    override fun onDoneButtonClick() {
        getUiStateData()?.let { data ->
            dataRequest(
                request = {
                    emotionsRepository.insertEmotion(
                        Emotion(
                            title = data.action,
                            emotions = data.emotions.filterIndexed { index, _ ->
                                data.chosenEmotions.contains(index)
                            },
                            emotionColor = EmotionColor.Anger,
                            tags = emptyList(),
                        ),
                    )
                },
                onSuccess = {
                    sendEvent(CloseBottomSheetEvent)
                },
            )
        }
    }

    private fun initData() {
        // todo: implement repository connection
        dataRequest(
            request = {
                EmotionUiState.Data(
                    action = String.empty,
                    whatHappened = String.empty,
                    emotions = emptyList(),
                    currentScreen = EmotionScreens.NewEmotion,
                    date = date.value.toTimeDateString(),
                )
            },
            onSuccess = { data ->
                _uiState.value = data
            },
        )
    }

    private fun getUiStateData(): EmotionUiState.Data? {
        return uiState.value as? EmotionUiState.Data
    }
}