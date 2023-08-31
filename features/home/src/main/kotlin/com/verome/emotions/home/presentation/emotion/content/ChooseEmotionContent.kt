package com.verome.emotions.home.presentation.emotion.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verome.core.domain.emotions.EmotionColor
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.bold
import com.verome.emotions.home.presentation.emotion.EmotionController
import com.verome.emotions.home.presentation.emotion.content.common.EmotionPreviewExt

@Composable
internal fun ChooseEmotionContent(controller: EmotionController) {
    Column {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "What are you feeling",
            style = MaterialTheme.typography.h4.bold(),
            modifier = Modifier.padding(start = 16.dp),
        )
        Spacer(modifier = Modifier.height(21.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            CircleEmotion(
                modifier = Modifier.align(Alignment.TopEnd),
                emotionColor = EmotionColor.Sadness,
                emotion = "\uD83D\uDE14",
                onClick = controller::onEmotionClick,
            )
            CircleEmotion(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 20.dp),
                emotionColor = EmotionColor.Joy,
                emotion = "\uD83D\uDE07",
                onClick = controller::onEmotionClick,
            )
            CircleEmotion(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 111.dp, start = 27.dp),
                emotionColor = EmotionColor.Fear,
                emotion = "\uD83D\uDE27",
                onClick = controller::onEmotionClick,
            )
            CircleEmotion(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 175.dp, start = 7.dp),
                emotionColor = EmotionColor.Shame,
                emotion = "\uD83D\uDE16",
                onClick = controller::onEmotionClick,
            )
            CircleEmotion(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 265.dp, end = 13.dp),
                emotionColor = EmotionColor.Anger,
                emotion = "\uD83D\uDE20",
                onClick = controller::onEmotionClick,
            )
        }
    }
}

@Composable
private fun CircleEmotion(
    modifier: Modifier = Modifier,
    emotionColor: EmotionColor,
    emotion: String,
    onClick: (color: EmotionColor) -> Unit,
) {
    Box(
        modifier = modifier
            .size(105.dp)
            .clip(CircleShape)
            .background(colorResource(emotionColor.color))
            .clickable { onClick.invoke(emotionColor) },
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = emotion, fontSize = 40.sp)
            Text(text = emotionColor.name, style = MaterialTheme.typography.h4.bold())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChooseEmotionContentPreview() {
    AppTheme {
        ChooseEmotionContent(
            controller = EmotionPreviewExt.FakeEmotionController(),
        )
    }
}