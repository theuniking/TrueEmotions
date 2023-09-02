package com.verome.emotions.home.presentation.emotion.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.bold
import com.verome.core.ui.widgets.button.DefaultButton
import com.verome.core.ui.widgets.tags.Emotion
import com.verome.emotions.home.presentation.emotion.EmotionController
import com.verome.emotions.home.presentation.emotion.EmotionUiState
import com.verome.emotions.home.presentation.emotion.content.common.EmotionPreviewExt
import com.verome.emotions.home.presentation.emotion.content.common.EmotionScreens

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ChosenEmotionContent(uiState: EmotionUiState.Data, controller: EmotionController) {
    if (uiState.currentScreen !is EmotionScreens.ChosenEmotion) return
    Column {
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Column {
                Text(
                    text = "Choose emotions you feel (up to 5)",
                    style = MaterialTheme.typography.subtitle1.bold(),
                )
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow {
                    uiState.emotions.forEachIndexed { index, emotion ->
                        Emotion(
                            modifier = Modifier
                                .padding(end = 8.dp, bottom = 8.dp),
                            tag = emotion,
                            color = colorResource(id = uiState.currentScreen.emotionColor.color),
                            isPicked = uiState.chosenEmotions.contains(index),
                            onClick = { controller.onEmotionTagClick(index) },
                        )
                    }
                }
            }
            Column {
                Spacer(modifier = Modifier.weight(1f))
                DefaultButton(
                    text = "Done",
                    onClick = controller::onDoneButtonClick,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(29.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChosenEmotionContentPreview() {
    AppTheme {
        ChosenEmotionContent(
            uiState = EmotionPreviewExt.uiState,
            controller = EmotionPreviewExt.FakeEmotionController(),
        )
    }
}