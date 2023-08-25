package com.verome.core.ui.widgets.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun Emotion(tag: String, color: Color, isPicked: Boolean = false) {
    Text(
        modifier = Modifier
            .padding(end = 4.dp, bottom = 6.dp)
            .clip(MaterialTheme.shapes.ButtonShape)
            .background(color = if (isPicked) color else Color.Transparent)
            .border(
                width = 1.dp,
                color = when {
                    !isPicked -> color
                    else -> Color.Transparent
                },
                shape = MaterialTheme.shapes.ButtonShape,
            )
            .padding(horizontal = 10.dp, vertical = 3.dp),
        text = tag,
        style = MaterialTheme.typography.subtitle2,
    )
}

@Preview
@Composable
private fun EmotionPreview() {
    AppTheme {
        Emotion(tag = "Emotion", color = MaterialTheme.additionalColors.icon)
    }
}