package com.verome.emotions.home.presentation.emotion.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.verome.core.domain.localization.string.toVmResStr
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.widgets.toolbar.BottomSheetToolbar
import com.verome.emotions.home.presentation.emotion.EmotionController
import com.verome.emotions.home.presentation.emotion.EmotionUiState
import com.verome.emotions.home.presentation.emotion.content.common.EmotionScreens

@Composable
internal fun EmotionContent(
    uiState: EmotionUiState,
    controller: EmotionController,
) {
    if (uiState is EmotionUiState.Data) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 700.dp, max = 700.dp),
        ) {
            BottomSheetToolbar(
                title = uiState.currentScreen.title.toVmResStr(),
                onBackClick = controller::onBackClick,
            )
            when (uiState.currentScreen) {
                is EmotionScreens.NewEmotion -> {
                    NewEmotionContent(
                        uiState = uiState,
                        controller = controller,
                    )
                }

                is EmotionScreens.ChooseEmotion -> {
                    ChooseEmotionContent(
                        controller = controller,
                    )
                }

                is EmotionScreens.ChosenEmotion -> {
                    ChosenEmotionContent(
                        uiState = uiState,
                        controller = controller,
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 700.dp, max = 700.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(color = MaterialTheme.additionalColors.btnText)
        }
    }
}