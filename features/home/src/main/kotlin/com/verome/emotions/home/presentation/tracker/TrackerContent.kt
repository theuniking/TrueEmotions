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
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.widgets.chart.rememberChartStyle
import com.verome.core.ui.widgets.chart.rememberMarker
import com.verome.core.ui.widgets.selector.MultiSelector
import com.verome.core.ui.widgets.toolbar.BottomSheetToolbar
import com.verome.emotions.home.presentation.tracker.range.TrackerRange
import java.time.ZoneOffset

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
                            uiState.trackerValues.keys.map { localDateTime ->
                                localDateTime.toEpochSecond(ZoneOffset.UTC).toFloat() to marker
                            }.toMap()
                        },
                    ),
                    model = entryModelOf(
                        uiState.trackerValues.map { (dateTime, emotionValue) ->
                            FloatEntry(
                                x = when (uiState.dateRange) {
                                    TrackerRange.WEEK -> {
                                        dateTime.toLocalDate().toEpochDay().toFloat()
                                    }
                                    TrackerRange.MONTH -> {
                                        dateTime.toLocalDate().toEpochDay().toFloat()
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
                        uiState.someAverage,
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

// TODO: uncomment
// @Preview(showSystemUi = true)
// @Composable
// private fun TrackerContentPreview() {
//    AppTheme {
//        class FakeTrackerController : TrackerController {
//            override fun onDateRangeChanged() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onBackClick() {
//                TODO("Not yet implemented")
//            }
//        }
//        TrackerContent(
//            uiState = TrackerUiState(),
//            controller = FakeTrackerController(),
//        )
//    }
// }