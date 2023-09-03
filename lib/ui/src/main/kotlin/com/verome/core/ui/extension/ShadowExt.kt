package com.verome.core.ui.extension

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.additionalColors

fun Modifier.defaultShadow(shape: Shape, elevation: Dp = 10.dp): Modifier = composed {
    this.shadow(
        elevation = elevation,
        shape = shape,
        spotColor = MaterialTheme.additionalColors.btnDisabledGradientFirst,
        ambientColor = MaterialTheme.additionalColors.btnDisabledGradientSecond,
    )
}