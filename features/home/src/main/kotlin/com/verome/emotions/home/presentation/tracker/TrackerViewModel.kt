package com.verome.emotions.home.presentation.tracker

import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.verome.core.ui.base.BaseViewModel
import com.verome.core.ui.extension.tryToUpdate
import com.verome.core.ui.navigation.CloseBottomSheetEvent
import com.verome.emotions.home.domain.repository.EmotionsRepository
import com.verome.emotions.home.presentation.tracker.range.TrackerRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

private const val DEFAULT_AVERAGE = 0f
private typealias TrackerValue = Map<LocalDateTime, Float>

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val emotionsRepository: EmotionsRepository,
) : BaseViewModel(), TrackerController {
    private val _uiState: MutableStateFlow<TrackerUiState> = MutableStateFlow(
        TrackerUiState.Loading,
    )
    internal val uiState: StateFlow<TrackerUiState> = _uiState.asStateFlow()

    init {
        initData()
    }

    override fun onDateRangeChanged(range: TrackerRange) {
        val trackerValues = getTrackerValuesFromRange(range)
        getUiStateData()?.let { data ->
            _uiState.tryToUpdate {
                data.copy(
                    dateRange = range,
                    trackerValues = trackerValues,
                    someAverage = getAverageFromTrackerValues(
                        trackerValues = trackerValues,
                    ),
                    formatter = calculateFormatter(
                        trackerValues = trackerValues,
                        range = range,
                    ),
                )
            }
        }
    }

    override fun onBackClick() {
        sendEvent(
            CloseBottomSheetEvent,
        )
    }

    private fun initData() {
        dataRequest(
            request = {
                var impactValue = 0f
                emotionsRepository.getEmotions().sortedBy {
                    it.date
                }.map { emotion ->
                    val trackerImpact = emotion.emotionColor.trackerImpact
                    impactValue += trackerImpact
                    emotion.date to impactValue
                }.toMap()
            },
            onSuccess = { data ->
                _uiState.value = TrackerUiState.Data(
                    emotionsValue = data.toSortedMap(),
                )
            },
            finally = {
                getUiStateData()?.let { data ->
                    _uiState.tryToUpdate {
                        val trackerValues = getTrackerValuesFromRange()
                        data.copy(
                            trackerValues = trackerValues,
                            formatter = calculateFormatter(trackerValues),
                            someAverage = getAverageFromTrackerValues(
                                trackerValues,
                            ),
                        )
                    }
                }
            },
        )
    }

    private fun getTrackerValuesFromRange(range: TrackerRange = TrackerRange.WEEK): TrackerValue {
        return getUiStateData()?.let { data ->
            when (range) {
                TrackerRange.WEEK -> {
                    val currentWeek = LocalDateTime.now()
                        .get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
                    data.emotionsValue.filterKeys { dateTime ->
                        val weekOfDateTime =
                            dateTime.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
                        weekOfDateTime == currentWeek
                    }.averageDays()
                }

                TrackerRange.MONTH -> {
                    val currentYearMonth = YearMonth.now()
                    data.emotionsValue.filterKeys { dateTime ->
                        val entryYearMonth = YearMonth.from(dateTime)
                        entryYearMonth == currentYearMonth
                    }.averageDays()
                }
            }
        } ?: emptyMap()
    }

    private fun getAverageFromTrackerValues(
        trackerValues: TrackerValue,
    ): Float = try {
        (trackerValues.values.sum()) / trackerValues.size
    } catch (e: ArithmeticException) {
        DEFAULT_AVERAGE
    }

    private fun getUiStateData(): TrackerUiState.Data? {
        return uiState.value as? TrackerUiState.Data
    }

    private fun calculateFormatter(
        trackerValues: TrackerValue,
        range: TrackerRange = TrackerRange.WEEK,
    ): AxisValueFormatter<AxisPosition.Horizontal.Bottom>? {
        return getUiStateData()?.let {
            val dateTimeFormatter: DateTimeFormatter = when (range) {
                TrackerRange.WEEK -> {
                    DateTimeFormatter.ofPattern("d")
                }

                TrackerRange.MONTH -> {
                    DateTimeFormatter.ofPattern("d MMM")
                }
            }
            AxisValueFormatter { value, _ ->
                (LocalDate.ofEpochDay(value.toLong())).format(dateTimeFormatter)
            }
        }
    }
}

private fun TrackerValue.averageDays(): TrackerValue = this.entries
    .groupBy { it.key.toLocalDate() }
    .mapValues { (_, entries) -> entries.map { it.value }.average().toFloat() }
    .map { (localDate, averageValue) ->
        LocalDateTime.of(
            localDate,
            LocalDate.now().atStartOfDay().toLocalTime(),
        ) to averageValue
    }.toMap()