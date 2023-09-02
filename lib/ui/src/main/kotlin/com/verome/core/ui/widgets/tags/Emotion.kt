package com.verome.core.ui.widgets.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.ButtonShape
import com.verome.core.ui.theme.additionalColors

@Composable
fun Emotion(
    modifier: Modifier = Modifier,
    tag: String,
    color: Color,
    isPicked: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.ButtonShape)
            .background(color = if (isPicked) color else Color.Transparent)
            .border(
                width = (1 * 1.2).dp,
                color = when {
                    !isPicked -> color
                    else -> Color.Transparent
                },
                shape = MaterialTheme.shapes.ButtonShape,
            )
            .clickable(
                enabled = onClick != null,
                onClick = { onClick?.invoke() },
            )
            .padding(horizontal = (10 * 1.2).dp, vertical = (3 * 1.2).dp),
        text = tag,
        style = MaterialTheme.typography.subtitle2,
    )
}

@Preview
@Composable
private fun EmotionPreview() {
    AppTheme {
        Emotion(tag = "Emotion", color = MaterialTheme.additionalColors.primaryIcon)
    }
}