package com.verome.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.verome.core.ui.R

val TrueEmotionsFontFamily = FontFamily(
    Font(R.font.sf_pro_display_regular),
    Font(R.font.sf_pro_display_bold, FontWeight.Bold),
    Font(R.font.sf_pro_display_semibold, FontWeight.SemiBold),
)

val TrueEmotionsTypography = Typography(
    h1 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (34 * 1.2).sp,
    ),
    h2 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (24 * 1.2).sp,
    ),
    h3 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (20 * 1.2).sp,
    ),
    h4 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (18 * 1.2).sp,
    ),
    h5 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (17 * 1.2).sp,
    ),
    body1 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (12 * 1.2).sp,
    ),
    body2 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (10 * 1.2).sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (16 * 1.2).sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (14 * 1.2).sp,
    ),
    button = TextStyle(
        fontFamily = TrueEmotionsFontFamily,
        color = CoreBlack,
        fontWeight = FontWeight.Normal,
        fontSize = (20 * 1.2).sp,
    ),
)

@Composable
fun TextStyle.fontText(): TextStyle {
    return this.copy(
        fontFamily = FontFamily(
            Font(R.font.sf_pro_text_bold, FontWeight.Bold),
        ),
        fontWeight = FontWeight.Bold,
        fontSize = (this.fontSize / 1.2),
    )
}

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
