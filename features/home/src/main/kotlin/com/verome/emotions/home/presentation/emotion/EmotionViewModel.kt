package com.verome.emotions.home.presentation.emotion

import androidx.lifecycle.SavedStateHandle
import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.emotions.Emotion.Companion.DEFAULT_FALSE_LONG
import com.verome.core.domain.emotions.Emotion.Companion.EMOTION_ID_NAME
import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.domain.empty
import com.verome.core.domain.repository.EmotionsCategoryRepository
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.external.app.service.Update
import com.verome.core.ui.external.app.service.UpdateService
import com.verome.core.ui.navigation.CloseBottomSheetEvent
import com.verome.core.ui.navigation.NavigateBackEvent
import com.verome.core.ui.utils.getMillis
import com.verome.core.ui.widgets.dialog.DialogControl
import com.verome.core.ui.widgets.dialog.picker.date.DatePickerDialogData
import com.verome.core.ui.widgets.dialog.picker.time.TimePickerDialogData
import com.verome.emotions.home.domain.repository.EmotionsRepository
import com.verome.emotions.home.presentation.emotion.content.common.EmotionScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

private const val NEW_EMOTION_NAME = "New Emotion"
private const val MAX_ACTION_CHARS = 100
private const val MAX_TAGS_MINUS_ONE = 4
private const val DEFAULT_HOUR = 12
private const val DEFAULT_MINUTE = 0
private const val ONE_DAY = 1L
private const val TAGS_SPLIT_BY = ","
private const val EMPTY_SPACE = " "

@HiltViewModel
class EmotionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val emotionsCategoryRepository: EmotionsCategoryRepository,
    private val emotionsRepository: EmotionsRepository,
    private val updateService: UpdateService,
) : BaseViewModel(), EmotionController {
    private val _uiState: MutableStateFlow<EmotionUiState> = MutableStateFlow(
        EmotionUiState.Loading,
    )
    internal val uiState: StateFlow<EmotionUiState> = _uiState.asStateFlow()
    private val date: MutableStateFlow<Long> = MutableStateFlow(LocalDate.now().getMillis())
    override val datePickerControl = DialogControl<DatePickerDialogData, Long>()
    override val timePickerControl = DialogControl<TimePickerDialogData, TimePickerDialogData>()

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
                is EmotionScreens.AddEditEmotion -> {
                    sendEvent(
                        NavigateBackEvent,
                    )
                }

                is EmotionScreens.ChooseEmotion -> {
                    _uiState.tryToUpdate {
                        data.copy(
                            currentScreen = EmotionScreens.AddEditEmotion(
                                isNewEmotion = data.currentScreen.title == NEW_EMOTION_NAME,
                            ),
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

    override fun onTagsFieldChange(tags: String) {
        getUiStateData()?.let { data ->
            _uiState.tryToUpdate {
                data.copy(
                    tags = tags,
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
                                date = LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(date.value),
                                    ZoneId.systemDefault(),
                                ).toLocalDate(),
                            )
                        }
                    }
                }
            },
        )
    }

    override fun onTimeChangeClick() {
        dataRequest(
            request = {
                timePickerControl.showForResult(
                    data = TimePickerDialogData(
                        selectedHour = getUiStateData()?.time?.hour ?: DEFAULT_HOUR,
                        selectedMinute = getUiStateData()?.time?.minute ?: DEFAULT_MINUTE,
                    ),
                )
            },
            onSuccess = { selectedDate ->
                getUiStateData()?.let { data ->
                    _uiState.tryToUpdate {
                        data.copy(
                            time = LocalTime.of(
                                selectedDate?.selectedHour ?: DEFAULT_HOUR,
                                selectedDate?.selectedMinute ?: DEFAULT_MINUTE,
                            ),
                        )
                    }
                }
            },
        )
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
                            emotionColor = emotionColor,
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
                            emotionId = data.emotionId,
                            action = data.action,
                            whatHappened = data.whatHappened,
                            emotions = data.emotions.filterIndexed { index, _ ->
                                data.chosenEmotions.contains(index)
                            },
                            emotionColor = data.emotionColor,
                            tags = data.tags.split(TAGS_SPLIT_BY).map { it.trim() }
                                .filter { it.isNotEmpty() },
                            date = LocalDateTime.of(
                                data.date,
                                data.time,
                            ),
                        ),
                    )
                },
                onSuccess = {
                    sendEvent(CloseBottomSheetEvent)
                    updateService.update(Update.EMOTIONS)
                },
            )
        }
    }

    private fun initData() {
        dataRequest(
            request = {
                savedStateHandle.get<Long>(EMOTION_ID_NAME)?.let { emotionId ->
                    if (emotionId == DEFAULT_FALSE_LONG) return@let null
                    emotionsRepository.getEmotionById(emotionId = emotionId)
                }
            },
            onSuccess = { data ->
                _uiState.value = data?.let {
                    date.tryToUpdate {
                        data.date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    }
                    EmotionUiState.Data(
                        emotionId = data.emotionId,
                        action = data.action,
                        whatHappened = data.whatHappened,
                        tags = data.tags.joinToString(TAGS_SPLIT_BY + EMPTY_SPACE),
                        date = data.date.toLocalDate(),
                        time = data.date.toLocalTime(),
                        emotions = data.emotions,
                        emotionColor = data.emotionColor,
                    )
                } ?: EmotionUiState.Data(
                    action = String.empty,
                    whatHappened = String.empty,
                    date = LocalDate.now(),
                    time = LocalTime.now(),
                    emotions = emptyList(),
                    tags = String.empty,
                    chosenEmotions = emptyList(),
                    emotionColor = EmotionColor(name = String.empty, color = 0, trackerImpact = 0f),
                )
            },
        )
    }

    private fun getUiStateData(): EmotionUiState.Data? {
        return uiState.value as? EmotionUiState.Data
    }
}