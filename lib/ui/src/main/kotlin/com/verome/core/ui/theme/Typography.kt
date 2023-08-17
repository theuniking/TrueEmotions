package com.verome.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val TrueEmotionsTypography = Typography()

@Composable
fun TextStyle.bold(): TextStyle {
    return this.copy(
        fontWeight = FontWeight.Bold,
    )
}
