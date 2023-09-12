package com.verome.emotions.home.presentation.tracker

import androidx.compose.runtime.Stable
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.verome.emotions.home.presentation.tracker.range.TrackerRange
import java.time.LocalDateTime

private const val DEFAULT_AVERAGE = 0f

@Stable
internal sealed interface TrackerUiState {
    data object Loading : TrackerUiState
    data class Data(
        val dateRange: TrackerRange = TrackerRange.WEEK,
        val emotionsValue: Map<LocalDateTime, Float>,
        val someAverage: Float = DEFAULT_AVERAGE,
        val trackerValues: Map<LocalDateTime, Float> = emptyMap(),
        val formatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom>? = null,
    ) : TrackerUiState
}
