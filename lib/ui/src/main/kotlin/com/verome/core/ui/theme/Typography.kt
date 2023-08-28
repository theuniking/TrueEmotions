package com.verome.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val TrueEmotionsTypography = Typography(
    h1 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
    ),
    h2 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    h3 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    h4 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
    ),
    body1 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    body2 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    ),
    subtitle1 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    button = TextStyle(
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
)

@Composable
fun TextStyle.bold(): TextStyle {
    return this.copy(
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun TextStyle.semiBold(): TextStyle {
    return this.copy(
        fontWeight = FontWeight.SemiBold,
    )
}
