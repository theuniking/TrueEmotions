package com.verome.emotions.home.presentation.home.navigation.bottom

import android.graphics.PointF
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

private const val CURVE_CIRCLE_RADIUS = 92f

// the coordinates of the first curve
private val mFirstCurveStartPoint = PointF()
private val mFirstCurveControlPoint1 = PointF()
private val mFirstCurveControlPoint2 = PointF()
private val mFirstCurveEndPoint = PointF()

private val mSecondCurveControlPoint1 = PointF()
private val mSecondCurveControlPoint2 = PointF()
private var mSecondCurveStartPoint = PointF()
private var mSecondCurveEndPoint = PointF()

class BottomNavShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                // get width and height of navigation bar
                // Navigation bar bounds (width & height)
                // the coordinates (x,y) of the start point before curve
                mFirstCurveStartPoint.set(
                    (size.width / 2) - (CURVE_CIRCLE_RADIUS * 2) - (CURVE_CIRCLE_RADIUS / 3),
                    0f,
                )
                // the coordinates (x,y) of the end point after curve
                mFirstCurveEndPoint.set(
                    size.width / 2,
                    CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4),
                )
                // same thing for the second curve
                mSecondCurveStartPoint = mFirstCurveEndPoint
                mSecondCurveEndPoint.set(
                    (size.width / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3),
                    0f,
                )

                // the coordinates (x,y)  of the 1st control point on a cubic curve
                mFirstCurveControlPoint1.set(
                    mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4),
                    mFirstCurveStartPoint.y,
                )
                // the coordinates (x,y)  of the 2nd control point on a cubic curve
                mFirstCurveControlPoint2.set(
                    mFirstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2) + CURVE_CIRCLE_RADIUS,
                    mFirstCurveEndPoint.y,
                )

                mSecondCurveControlPoint1.set(
                    mSecondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS,
                    mSecondCurveStartPoint.y,
                )
                mSecondCurveControlPoint2.set(
                    mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)),
                    mSecondCurveEndPoint.y,
                )

                reset()
                moveTo(0f, 0f)
                lineTo(
                    mFirstCurveStartPoint.x,
                    mFirstCurveStartPoint.y,
                )

                cubicTo(
                    mFirstCurveControlPoint1.x,
                    mFirstCurveControlPoint1.y,
                    mFirstCurveControlPoint2.x,
                    mFirstCurveControlPoint2.y,
                    mFirstCurveEndPoint.x,
                    mFirstCurveEndPoint.y,
                )

                cubicTo(
                    mSecondCurveControlPoint1.x,
                    mSecondCurveControlPoint1.y,
                    mSecondCurveControlPoint2.x,
                    mSecondCurveControlPoint2.y,
                    mSecondCurveEndPoint.x,
                    mSecondCurveEndPoint.y,
                )

                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            },
        )
    }
}