package com.verome.core.ui.widgets.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.domain.emotions.Emotion
import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.ui.extension.defaultShadow
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.fontText
import com.verome.core.ui.widgets.tags.Emotion
import com.verome.core.ui.widgets.tags.Tag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmotionCard(
    modifier: Modifier = Modifier,
    emotion: Emotion,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultShadow(shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.additionalColors.coreWhite,
    ) {
        Column(
            modifier = Modifier
                .padding(start = (12 * 1.2).dp, top = (8 * 1.2).dp, bottom = (8 * 1.2).dp, end = (7 * 1.2).dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FlowRow {
                    emotion.tags.forEach { tag ->
                        Tag(tag = tag)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = emotion.date.toString(),
                    style = MaterialTheme.typography.body2,
                )
            }
            Spacer(modifier = Modifier.height((10 * 1.2).dp))
            Text(text = emotion.action, style = MaterialTheme.typography.subtitle1.fontText())
            Spacer(modifier = Modifier.height((6 * 1.2).dp))
            FlowRow {
                emotion.tags.forEach { tag ->
                    Emotion(
                        modifier = Modifier
                            .padding(end = (4 * 1.2).dp, bottom = (6 * 1.2).dp),
                        tag = tag,
                        color = colorResource(id = emotion.emotionColor.color),
                        isPicked = true,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EmotionCardPreview() {
    AppTheme {
        EmotionCard(
            emotion = Emotion(
                action = "Day of Joy",
                whatHappened = "A lot",
                tags = listOf(
                    "Work",
                    "Cat",
                    "Good weather",
                ),
                emotions = listOf(
                    "Fun",
                    "Jubilation",
                    "Delight",
                ),
                emotionColor = EmotionColor.Joy,
            ),
        )
    }
}