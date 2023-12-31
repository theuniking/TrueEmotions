package com.verome.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val shapes = Shapes(
    small = RoundedCornerShape((4 * 1.2).dp),
    medium = RoundedCornerShape((8 * 1.2).dp),
    large = RoundedCornerShape((16 * 1.2).dp),
)

val Shapes.BottomSheetShape
    @Composable get() = RoundedCornerShape((16 * 1.2).dp, (16 * 1.2).dp)

val Shapes.ButtonShape
    @Composable get() = RoundedCornerShape(50)