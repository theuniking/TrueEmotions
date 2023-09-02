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
val Grey60 = Color(0xFFACAEB3)
val Grey70 = Color(0xFFBFBFBF)
val Grey90 = Color(0xFFECF1FD)
val Indigo30 = Color(0xFF5445F9)
val Indigo50 = Color(0xFF7157FC)
val Indigo70 = Color(0xFF8F68FF)
val Indigo90 = Color(0xFFAFB7FF)
val Blue80 = Color(0xFF83AFFF)
val Red70 = Color(0xFFD97C7C)
val Green70 = Color(0xFF8DE4A5)

data class AdditionalColors(
    val coreWhite: Color,
    val coreBlack: Color,
    val coreGradientFirst: Color,
    val coreGradientSecond: Color,
    val background: Color,
    val textFieldBorder: Color,
    val tagBorder: Color,
    val error: Color,
    val correct: Color,
    val textTrick: Color,
    val textMain: Color,
    val primaryIcon: Color,
    val secondaryIcon: Color,
    val textLight: Color,
    val btnText: Color,
    val btnDisabledGradientFirst: Color,
    val btnDisabledGradientSecond: Color,
    val actionCard1: Color,
)

val additionalLightColors = AdditionalColors(
    coreWhite = CoreWhite,
    coreBlack = CoreBlack,
    coreGradientFirst = Indigo70,
    coreGradientSecond = Indigo30,
    background = White,
    textFieldBorder = Blue80,
    tagBorder = Grey90,
    error = Red70,
    correct = Green70,
    textTrick = Grey50,
    textMain = CoreBlack,
    primaryIcon = Grey40,
    secondaryIcon = Grey60,
    textLight = CoreWhite,
    btnText = Indigo50,
    btnDisabledGradientFirst = Grey70,
    btnDisabledGradientSecond = Grey30,
    actionCard1 = Indigo90,
)

val LocalAdditionalColorsProvider = staticCompositionLocalOf<AdditionalColors> {
    error("No default colors provided")
}

val MaterialTheme.additionalColors: AdditionalColors
    @Composable
    get() {
        return LocalAdditionalColorsProvider.current
    }
