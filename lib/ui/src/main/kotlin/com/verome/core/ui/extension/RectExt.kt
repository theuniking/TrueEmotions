package com.verome.core.ui.extension

import com.patrykandpatrick.vico.core.component.shape.Shape
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.CorneredShape
import com.patrykandpatrick.vico.core.component.shape.cornered.RoundedCornerTreatment

/**
 * Creates a [Shape] with all corners rounded.
 *
 * @param allPercent the radius of each corner (in percent).
 */
fun roundedCornerShape(allPercent: Int): CorneredShape =
    roundedCornerShape(allPercent, allPercent, allPercent, allPercent)

/**
 * Creates a [Shape] with all corners rounded.
 *
 * @param topLeftPercent the top-left corner radius (in percent).
 * @param topRightPercent the top-right corner radius (in percent).
 * @param bottomRightPercent the bottom-right corner radius (in percent).
 * @param bottomLeftPercent the bottom-left corner radius (in percent).
 */
fun roundedCornerShape(
    topLeftPercent: Int = 0,
    topRightPercent: Int = 0,
    bottomRightPercent: Int = 0,
    bottomLeftPercent: Int = 0,
): CorneredShape = CorneredShape(
    Corner.Relative(topLeftPercent, RoundedCornerTreatment),
    Corner.Relative(topRightPercent, RoundedCornerTreatment),
    Corner.Relative(bottomRightPercent, RoundedCornerTreatment),
    Corner.Relative(bottomLeftPercent, RoundedCornerTreatment),
)