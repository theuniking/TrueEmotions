package com.verome.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val CoreWhite = Color(0xFFFFFFFF)
val CoreBlack = Color(0xFF2E2E2E)
val White = Color(0xFFF6F9FF)
val Grey30 = Color(0xFF7D7D7D)
val Grey40 = Color(0xFF848484)
val Grey50 = Color(0xFF8A8A8A)
val Grey70 = Color(0xFFBFBFBF)
val Indigo30 = Color(0xFF5445F9)
val Indigo50 = Color(0xFF7157FC)
val Indigo70 = Color(0xFF8F68FF)
val Blue80 = Color(0xFF83AFFF)
val Red70 = Color(0xFFD97C7C)

data class AdditionalColors(
    val coreWhite: Color,
    val coreBlack: Color,
    val coreGradientFirst: Color,
    val coreGradientSecond: Color,
    val background: Color,
    val stroke: Color,
    val error: Color,
    val textTrick: Color,
    val textMain: Color,
    val icon: Color,
    val textLight: Color,
    val btnText: Color,
    val btnDisabledGradientFirst: Color,
    val btnDisabledGradientSecond: Color,
)

val additionalLightColors = AdditionalColors(
    coreWhite = CoreWhite,
    coreBlack = CoreBlack,
    coreGradientFirst = Indigo70,
    coreGradientSecond = Indigo30,
    background = White,
    stroke = Blue80,
    error = Red70,
    textTrick = Grey50,
    textMain = CoreBlack,
    icon = Grey40,
    textLight = CoreWhite,
    btnText = Indigo50,
    btnDisabledGradientFirst = Grey70,
    btnDisabledGradientSecond = Grey30,
)

val LocalAdditionalColorsProvider = staticCompositionLocalOf<AdditionalColors> {
    error("No default colors provided")
}

val MaterialTheme.additionalColors: AdditionalColors
    @Composable
    get() {
        return LocalAdditionalColorsProvider.current
    }
