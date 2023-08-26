package com.verome.core.ui.extension

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.additionalColors

fun Modifier.defaultShadow(shape: Shape): Modifier = composed {
    this.shadow(
        elevation = 10.dp,
        shape = shape,
        spotColor = MaterialTheme.additionalColors.btnDisabledGradientFirst,
        ambientColor = MaterialTheme.additionalColors.btnDisabledGradientSecond,
    )
}