package com.verome.emotions.home.presentation.tracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.verome.core.domain.localization.string.toVmResStr
import com.verome.core.ui.widgets.toolbar.BottomSheetToolbar
import com.verome.emotions.home.presentation.tracker.graph.Graph
import kotlin.random.Random

@Composable
internal fun TrackerContent(
    uiState: TrackerUiState,
    controller: TrackerController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 600.dp, max = 600.dp),
    ) {
        BottomSheetToolbar(
            title = "Tracker".toVmResStr(),
            onBackClick = controller::onBackClick,
        )
        Spacer(modifier = Modifier.height(40.dp))
        GraphContent(uiState.trackerValues.map { it.toFloat() })
    }
}

@Composable
private fun GraphContent(
    trackerValues: List<Float>,
) {
    // todo: remove
    val yStep = 1
    val random = Random
    /* to test with random points */
    val points = (0..9).map {
        var num = random.nextInt(10)
        num.toFloat()
    }
    Graph(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp),
        xValues = (0..9).map { it + 1 },
        yValues = (-10..10).map { (it) * yStep },
        points = trackerValues,
        paddingSpace = 16.dp,
        verticalStep = yStep,
    )
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