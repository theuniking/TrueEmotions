package com.verome.core.ui.widgets.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.extension.defaultShadow
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.ButtonShape
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.extension.zeroElevation

@Composable
fun DefaultButton(
    text: String?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.button.bold(),
    colors: Brush? = null,
    shape: CornerBasedShape = MaterialTheme.shapes.ButtonShape,
    contentPadding: PaddingValues = PaddingValues(vertical = 17.dp),
    content: @Composable () -> Unit = {
        DefaultButtonContent(
            text = text,
            style = style,
            modifier = Modifier.padding(contentPadding).fillMaxWidth(),
            color = MaterialTheme.additionalColors.coreWhite,
        )
    },
    paddingValuesOutside: PaddingValues = PaddingValues(horizontal = (50 / 1.2).dp),
) {
    Button(
        modifier = Modifier
            .padding(paddingValuesOutside)
            .defaultShadow(shape = shape),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.zeroElevation(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
        ),
        shape = shape,
    ) {
        Box(
            modifier = modifier
                .background(
                    brush = when {
                        colors != null -> colors
                        enabled -> {
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.additionalColors.coreGradientFirst,
                                    MaterialTheme.additionalColors.coreGradientSecond,
                                ),
                            )
                        }

                        else -> {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.additionalColors.btnDisabledGradientFirst,
                                    MaterialTheme.additionalColors.btnDisabledGradientSecond,
                                ),
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.ButtonShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Composable
internal fun DefaultButtonContent(
    modifier: Modifier = Modifier,
    text: String?,
    style: TextStyle,
    color: Color,
) {
    text?.let {
        Text(
            modifier = modifier,
            text = text,
            textAlign = TextAlign.Center,
            style = style,
            color = color,
            fontFamily = FontFamily.Default,
        )
    }
}

@Preview
@Composable
private fun DefaultButtonPreview() {
    AppTheme {
        DefaultButton(
            text = "Preview",
            enabled = true,
            onClick = {},
        )
    }
}