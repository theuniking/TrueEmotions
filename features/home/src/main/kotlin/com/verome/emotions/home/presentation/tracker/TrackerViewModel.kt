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
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Year
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

private const val DEFAULT_AVERAGE = 0f
private const val DEFAULT_IMPACT_VALUE = 0f
private const val DEFAULT_MONTH_DAY = 1
private const val DEFAULT_MINUTE = 1
private const val MONTH_COUNT = 12
private const val SECOND_TO_HOUR = 3600f
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

    override fun monthsToFloat(date: LocalDate): Float {
        val year = date.year
        val monthValue = date.monthValue
        return (year * MONTH_COUNT + monthValue - DEFAULT_MONTH_DAY).toFloat()
    }

    override fun hoursToFloat(dateTime: LocalDateTime): Float =
        dateTime.toInstant(ZoneOffset.UTC).epochSecond / SECOND_TO_HOUR

    private fun floatToMonths(float: Float): LocalDate {
        val years = float.toInt() / MONTH_COUNT
        val months = (float.toInt() % MONTH_COUNT) + DEFAULT_MONTH_DAY
        return LocalDate.of(LocalDate.now().year + years, months, DEFAULT_MONTH_DAY)
    }

    private fun floatToHours(float: Float): LocalDateTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond((float * SECOND_TO_HOUR).toLong()),
            ZoneOffset.UTC,
        )

    private fun initData() {
        dataRequest(
            request = {
                var impactValue = DEFAULT_IMPACT_VALUE
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
                            formatter = calculateFormatter(),
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
                TrackerRange.DAY -> {
                    val currentDate = LocalDate.now()
                    data.emotionsValue.filterKeys { dateTime ->
                        val entryDate = dateTime.toLocalDate()
                        entryDate == currentDate
                    }.averageHours()
                }

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

                TrackerRange.YEAR -> {
                    val currentYear = Year.now()
                    data.emotionsValue.filterKeys { dateTime ->
                        val entryYear = Year.from(dateTime)
                        entryYear == currentYear
                    }.averageMonth()
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
        range: TrackerRange = TrackerRange.WEEK,
    ): AxisValueFormatter<AxisPosition.Horizontal.Bottom>? {
        return getUiStateData()?.let {
            when (range) {
                TrackerRange.DAY -> {
                    AxisValueFormatter { value, _ ->
                        floatToHours(value).format(DateTimeFormatter.ofPattern("HH"))
                    }
                }

                TrackerRange.WEEK -> {
                    AxisValueFormatter { value, _ ->
                        (LocalDate.ofEpochDay(value.toLong())).format(DateTimeFormatter.ofPattern("d"))
                    }
                }

                TrackerRange.MONTH -> {
                    AxisValueFormatter { value, _ ->
                        (LocalDate.ofEpochDay(value.toLong())).format(DateTimeFormatter.ofPattern("d MMM"))
                    }
                }

                TrackerRange.YEAR -> {
                    AxisValueFormatter { value, _ ->
                        floatToMonths(value).format(DateTimeFormatter.ofPattern("MMM"))
                    }
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
                LocalTime.now(),
            ) to averageValue
        }.toMap()

    private fun TrackerValue.averageMonth(): TrackerValue = this.entries
        .groupBy { YearMonth.from(it.key) }
        .mapValues { (_, entries) -> entries.map { it.value }.average().toFloat() }
        .map { (yearMonth, averageValue) ->
            LocalDateTime.of(
                LocalDate.of(yearMonth.year, yearMonth.month, DEFAULT_MONTH_DAY),
                LocalTime.now(),
            ) to averageValue
        }.toMap()

    private fun TrackerValue.averageHours(): TrackerValue = this.entries
        .groupBy { it.key.withMinute(DEFAULT_MINUTE).withSecond(DEFAULT_MINUTE) } // Round minutes to 0
        .mapValues { (_, entries) -> entries.map { it.value }.average().toFloat() }
        .map { (yearMonth, averageValue) ->
            LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(
                    yearMonth.hour,
                    DEFAULT_MINUTE,
                ),
            ) to averageValue
        }.toMap()
}