package com.verome.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val CoreWhite = Color(0xFFFFFFFF)
val CoreBlack = Color(0xFF000000)

data class AdditionalColors(
    val coreWhite: Color,
    val coreBlack: Color,
    val backgroundPrimary: Color,
)

val additionalLightColors = AdditionalColors(
    coreWhite = CoreWhite,
    coreBlack = CoreBlack,
    backgroundPrimary = CoreWhite,
)

val LocalAdditionalColorsProvider = staticCompositionLocalOf<AdditionalColors> {
    error("No default colors provided")
}

val MaterialTheme.additionalColors: AdditionalColors
    @Composable
    get() {
        return LocalAdditionalColorsProvider.current
    }
