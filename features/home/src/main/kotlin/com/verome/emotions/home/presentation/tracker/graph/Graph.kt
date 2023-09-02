package com.verome.emotions.home.presentation.tracker.graph

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Graph(
    modifier: Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int,
    textSp: TextUnit = 14.sp,
) {
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = Color(0xFF8A8A8A).toArgb()
            textAlign = Paint.Align.RIGHT
            textSize = density.run { textSp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val xAxisSpace = (size.width - paddingSpace.toPx() / 4) / xValues.size
            val yAxisSpace = size.height / yValues.size
            /** placing y axis points */
            for (i in yValues.indices) {
                if (i % 10 == 0) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "${yValues[i]}",
                        paddingSpace.toPx() * 1.5f,
                        size.height - yAxisSpace * (i + 1) + (textSp.toPx() / 2.5f),
                        textPaint,
                    )
                    drawLine(
                        start = Offset(x = paddingSpace.toPx() * 2f, y = size.height - yAxisSpace * (i + 1)),
                        end = Offset(x = size.width, y = size.height - yAxisSpace * (i + 1)),
                        color = Color(0xFF8A8A8A),
                        strokeWidth = 2f,
                    )
                }
            }
            /** placing our x axis points */
            for (i in points.indices) {
                val x1 = xAxisSpace * xValues[i]
                val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                coordinates.add(PointF(x1, y1))
            }
            /** calculating the connection points */
            for (i in 1 until coordinates.size) {
                controlPoints1.add(PointF((coordinates[i].x + coordinates[i - 1].x) / 2, coordinates[i - 1].y))
                controlPoints2.add(PointF((coordinates[i].x + coordinates[i - 1].x) / 2, coordinates[i].y))
            }
            /** drawing the path */
            val stroke = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                for (i in 0 until 9) {
                    cubicTo(
                        controlPoints1[i].x,
                        controlPoints1[i].y,
                        controlPoints2[i].x,
                        controlPoints2[i].y,
                        coordinates[i + 1].x,
                        coordinates[i + 1].y,
                    )
                }
            }

            /** drawing the path */
            val fillPath = android.graphics.Path(stroke.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(xAxisSpace * xValues.last(), size.height - yAxisSpace)
                    lineTo(xAxisSpace, size.height - yAxisSpace)
                    close()
                }
            drawPath(
                fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x5798C7FF),
                        Color(0x574E31FF),
                    ),
                ),
            )
            drawPath(
                stroke,
                color = Color(0xFF7157FC),
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round,
                ),
            )
            for (i in 1 until 9) {
                /** drawing circles to indicate all the points */
                drawCircle(
                    color = Color(0xFF7157FC),
                    radius = textSp.toPx() / 2,
                    center = Offset(
                        controlPoints1[i].x - paddingSpace.toPx(),
                        controlPoints1[i].y,
                    ),
                )
                drawCircle(
                    color = Color.White,
                    radius = textSp.toPx() / 2 - 5f,
                    center = Offset(
                        controlPoints1[i].x - paddingSpace.toPx(),
                        controlPoints1[i].y,
                    ),
                )
            }
        }
    }
}