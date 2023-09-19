package com.verome.emotions.home.presentation.tracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.verome.core.domain.localization.string.toVmResStr
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.widgets.chart.rememberChartStyle
import com.verome.core.ui.widgets.chart.rememberMarker
import com.verome.core.ui.widgets.selector.MultiSelector
import com.verome.core.ui.widgets.toolbar.BottomSheetToolbar
import com.verome.emotions.home.presentation.tracker.range.TrackerRange
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

private const val DEFAULT_NAN_VALUE = 0f
private const val DEFAULT_MONTH_DAY = 1
private const val DEFAULT_MINUTE = 1

@Composable
internal fun TrackerContent(
    uiState: TrackerUiState,
    controller: TrackerController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 700.dp, max = 700.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BottomSheetToolbar(
            title = "Tracker".toVmResStr(),
            onBackClick = controller::onBackClick,
        )
        if (uiState is TrackerUiState.Data) {
            val marker = rememberMarker()
            Spacer(modifier = Modifier.height((40 * 1.2).dp))
            ProvideChartStyle(rememberChartStyle(listOf(MaterialTheme.additionalColors.btnText))) {
                Chart(
                    chart = lineChart(
                        persistentMarkers = remember(uiState.trackerValues) {
                            uiState.trackerValues.keys.associate { localDateTime ->
                                when (uiState.dateRange) {
                                    TrackerRange.DAY -> {
                                        controller.hoursToFloat(
                                            LocalDateTime.of(
                                                LocalDate.now(),
                                                LocalTime.of(
                                                    localDateTime.hour,
                                                    DEFAULT_MINUTE,
                                                ),
                                            ),
                                        )
                                    }

                                    TrackerRange.WEEK -> {
                                        localDateTime.toLocalDate().toEpochDay().toFloat()
                                    }

                                    TrackerRange.MONTH -> {
                                        localDateTime.toLocalDate().toEpochDay().toFloat()
                                    }

                                    TrackerRange.YEAR -> {
                                        val yearMonth = YearMonth.from(localDateTime)
                                        controller.monthsToFloat(
                                            LocalDate.of(
                                                yearMonth.year,
                                                yearMonth.month,
                                                DEFAULT_MONTH_DAY,
                                            ),
                                        )
                                    }
                                } to marker
                            }
                        },
                    ),
                    model = entryModelOf(
                        uiState.trackerValues.map { (dateTime, emotionValue) ->
                            FloatEntry(
                                x = when (uiState.dateRange) {
                                    TrackerRange.DAY -> {
                                        controller.hoursToFloat(
                                            LocalDateTime.of(
                                                LocalDate.now(),
                                                LocalTime.of(
                                                    dateTime.hour,
                                                    DEFAULT_MINUTE,
                                                ),
                                            ),
                                        )
                                    }

                                    TrackerRange.WEEK -> {
                                        dateTime.toLocalDate().toEpochDay().toFloat()
                                    }

                                    TrackerRange.MONTH -> {
                                        dateTime.toLocalDate().toEpochDay().toFloat()
                                    }

                                    TrackerRange.YEAR -> {
                                        val yearMonth = YearMonth.from(dateTime)
                                        controller.monthsToFloat(
                                            LocalDate.of(
                                                yearMonth.year,
                                                yearMonth.month,
                                                DEFAULT_MONTH_DAY,
                                            ),
                                        )
                                    }
                                },
                                y = emotionValue,
                            )
                        },
                    ),
                    marker = marker,
                    bottomAxis = rememberBottomAxis(
                        guideline = null,
                        valueFormatter = uiState.formatter ?: DecimalFormatAxisValueFormatter(),
                    ),
                    startAxis = rememberStartAxis(
                        itemPlacer = AxisItemPlacer.Vertical.default(),
                    ),
                )
            }
            Spacer(modifier = Modifier.height((33 * 1.2).dp))
            Text(
                text = "${uiState.dateRange.average} average: ${
                    String.format(
                        "%.1f",
                        uiState.someAverage.let {
                            if (it.isNaN()) DEFAULT_NAN_VALUE else it
                        },
                    )
                }\uD83D\uDE07",
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(modifier = Modifier.height((70 * 1.2).dp))
            MultiSelector(
                options = TrackerRange.values(),
                selectedOption = uiState.dateRange,
                onOptionSelect = controller::onDateRangeChanged,
                modifier = Modifier.padding(horizontal = (16 * 1.2).dp),
            )
        } else {
            CircularProgressIndicator(color = MaterialTheme.additionalColors.btnText)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TrackerContentPreview() {
    AppTheme {
        class FakeTrackerController : TrackerController {
            override fun onBackClick() = Unit
            override fun onDateRangeChanged(range: TrackerRange) = Unit
            override fun monthsToFloat(date: LocalDate): Float = Float.MIN_VALUE
            override fun hoursToFloat(dateTime: LocalDateTime): Float = Float.MIN_VALUE
        }
        TrackerContent(
            uiState = TrackerUiState.Data(
                emotionsValue = emptyMap(),
            ),
            controller = FakeTrackerController(),
        )
    }
}